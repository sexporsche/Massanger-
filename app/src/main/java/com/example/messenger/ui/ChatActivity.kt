package com.example.messenger.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.R
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.SessionStore
import com.example.messenger.data.entities.MessageEntity
import com.example.messenger.databinding.ActivityChatBinding
import com.example.messenger.network.SocketManager
import com.example.messenger.utils.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private var currentUserId: Int = 0
    private var recipientId: Int = 0
    private var recipientUsername: String = ""
    private var peerOnline: Boolean = false
    private var isListening = false
    private var lastMessageId: Long = 0L
    private var isGroupChat: Boolean = false
    private var groupChatKey: Int = 0

    companion object {
        private const val TAG = "MessengerChat"
        private const val REQUEST_PICK_FILE = 1001
        private const val REQUEST_NOTIFICATION_PERMISSION = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MessageRepository.init(applicationContext)

        recipientId = intent.getIntExtra("userId", 0)
        recipientUsername = intent.getStringExtra("username") ?: "Unknown"
        currentUserId = intent.getIntExtra("currentUserId", 0)
        peerOnline = intent.getBooleanExtra("isOnline", false)
        isGroupChat = intent.getBooleanExtra("isGroupChat", false)
        groupChatKey = intent.getIntExtra("groupChatKey", 0)

        binding.toolbarChat.setNavigationOnClickListener { finish() }
        binding.toolbarChat.title = ""
        if (isGroupChat && groupChatKey != 0) {
            binding.tvToolbarTitle.text = intent.getStringExtra("groupTitle") ?: "Группа"
            binding.tvToolbarStatus.text = getString(R.string.chat_group_mode_hint)
            binding.btnSend.isEnabled = false
            binding.etMessage.isEnabled = false
            binding.btnAttach.isEnabled = false
        } else {
            binding.tvToolbarTitle.text = recipientUsername
            updateToolbarStatus()
        }

        NotificationHelper.createNotificationChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            }
        }

        adapter = ChatAdapter(currentUserId)
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        (binding.rvMessages.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        binding.rvMessages.setHasFixedSize(false)

        observeConversation()

        binding.btnSend.setOnClickListener {
            if (isGroupChat) return@setOnClickListener
            val text = binding.etMessage.text?.toString()?.trim().orEmpty()
            if (text.isNotEmpty()) {
                sendMessage(text)
                binding.etMessage.text?.clear()
            }
        }
        binding.btnAttach.setOnClickListener {
            if (isGroupChat) return@setOnClickListener
            openFilePicker()
        }

        isListening = true
        if (!isGroupChat) {
            startPollingLoop()
        }
    }

    private fun updateToolbarStatus() {
        binding.tvToolbarStatus.text = if (peerOnline) {
            getString(R.string.status_online)
        } else {
            getString(R.string.status_offline)
        }
    }

    private fun observeConversation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val flow = if (isGroupChat && groupChatKey != 0) {
                    MessageRepository.get().groupConversationFlow(groupChatKey)
                } else {
                    MessageRepository.get().conversationFlow(currentUserId, recipientId)
                }
                flow.collect { list ->
                    android.util.Log.d(
                        TAG,
                        "Room emit: size=${list.size} ids=${list.joinToString { it.id.toString() }}"
                    )
                    val snapshot = ArrayList(list)
                    lastMessageId = snapshot.maxOfOrNull { it.id } ?: 0L
                    binding.tvEmptyChat.isVisible = snapshot.isEmpty()
                    adapter.submitList(snapshot) {
                        android.util.Log.d(TAG, "submitList commit, itemCount=${adapter.itemCount}")
                        if (snapshot.isNotEmpty()) {
                            binding.rvMessages.post {
                                binding.rvMessages.smoothScrollToPosition(snapshot.size - 1)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startPollingLoop() {
        lifecycleScope.launch(Dispatchers.IO) {
            pullAndMergeMessages(tag = "initial")
            while (isActive && isListening) {
                delay(3000)
                pullAndMergeMessages(tag = "poll")
            }
        }
    }

    private suspend fun pullAndMergeMessages(tag: String) {
        if (isGroupChat) return
        try {
            if (!SessionStore.hasCredentials(this@ChatActivity)) {
                android.util.Log.w(TAG, "[$tag] skip pull: no credentials")
                return
            }
            if (!authenticateSocket()) {
                android.util.Log.e(TAG, "[$tag] socket AUTH failed")
                return
            }
            SocketManager.sendLine("GET_MESSAGES:$recipientId")
            val response = SocketManager.readLine()
            android.util.Log.d(TAG, "[$tag] GET_MESSAGES response len=${response?.length} head=${response?.take(80)}")
            if (response?.startsWith("MESSAGES:") == true) {
                val jsonStr = response.substringAfter("MESSAGES:")
                val jsonArray = org.json.JSONArray(jsonStr)
                val batch = ArrayList<MessageEntity>(jsonArray.length())
                for (i in 0 until jsonArray.length()) {
                    val msgObj = jsonArray.getJSONObject(i)
                    batch.add(jsonToEntity(msgObj))
                }
                if (batch.isNotEmpty()) {
                    MessageRepository.get().insertAllLocal(batch)
                    android.util.Log.d(TAG, "[$tag] merged ${batch.size} messages from server")
                }
            } else if (response?.startsWith("ERROR:") == true) {
                android.util.Log.e(TAG, "[$tag] server error: $response")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "[$tag] pull error", e)
        } finally {
            SocketManager.disconnect()
        }
    }

    private suspend fun authenticateSocket(): Boolean = withContext(Dispatchers.IO) {
        val host = getString(R.string.server_host)
        val port = resources.getInteger(R.integer.server_port)
        val u = SessionStore.username(this@ChatActivity) ?: return@withContext false
        val p = SessionStore.password(this@ChatActivity) ?: return@withContext false
        SocketManager.connect(applicationContext, host, port)
        SocketManager.sendLine("AUTH:$u:$p")
        val r = SocketManager.readLine()
        android.util.Log.d(TAG, "socket AUTH -> $r")
        if (r?.startsWith("AUTH_OK:") != true) {
            SocketManager.disconnect()
            return@withContext false
        }
        true
    }

    private fun jsonToEntity(msgObj: JSONObject): MessageEntity {
        val msgId = msgObj.getLong("id")
        val senderId = msgObj.getInt("sender_id")
        val recId = msgObj.getInt("recipient_id")
        val messageText = when {
            msgObj.isNull("text") -> null
            else -> msgObj.optString("text", "").ifBlank { null }
        }
        val filePath = when {
            msgObj.isNull("file_path") -> null
            else -> msgObj.optString("file_path", "").ifBlank { null }
        }
        val ts = parseServerTimestamp(msgObj)
        return MessageEntity(
            id = msgId,
            senderId = senderId,
            recipientId = recId,
            groupId = null,
            text = messageText,
            filePath = filePath,
            timestamp = ts,
            isRead = false
        )
    }

    /** Python сервер хранит time.time() в секундах; клиент — millis */
    private fun parseServerTimestamp(msgObj: JSONObject): Long {
        if (!msgObj.has("timestamp")) return System.currentTimeMillis()
        val raw = when (val v = msgObj.get("timestamp")) {
            is Number -> v.toDouble()
            else -> msgObj.optString("timestamp", "0").toDoubleOrNull() ?: return System.currentTimeMillis()
        }
        return if (raw < 1_000_000_000_000.0) (raw * 1000).toLong() else raw.toLong()
    }

    private fun sendMessage(text: String) {
        if (isGroupChat) return
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                if (!SessionStore.hasCredentials(this@ChatActivity)) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ChatActivity, R.string.error_auth_socket, Toast.LENGTH_LONG).show()
                    }
                    return@launch
                }
                if (!authenticateSocket()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ChatActivity, R.string.error_auth_socket, Toast.LENGTH_LONG).show()
                    }
                    return@launch
                }
                val msgData = JSONObject().apply {
                    put("recipient_id", recipientId)
                    put("text", text)
                }
                SocketManager.sendLine("SEND_MSG:$msgData")
                val response = SocketManager.readLine()
                android.util.Log.d(TAG, "SEND_MSG response: $response")
                if (response?.startsWith("MSG_SENT:") == true) {
                    val msgId = response.substringAfter("MSG_SENT:").toLongOrNull() ?: 0L
                    val msg = MessageEntity(
                        id = msgId,
                        senderId = currentUserId,
                        recipientId = recipientId,
                        groupId = null,
                        text = text,
                        filePath = null,
                        timestamp = System.currentTimeMillis(),
                        isRead = false
                    )
                    MessageRepository.get().insertLocal(msg)
                    android.util.Log.d(TAG, "local insert after send id=$msgId")
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ChatActivity, getString(R.string.error_send) + ": $response", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "sendMessage", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, "${getString(R.string.error_send)}: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } finally {
                SocketManager.disconnect()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf(
                    "image/*",
                    "application/pdf",
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                    "text/plain"
                )
            )
        }
        @Suppress("DEPRECATION")
        startActivityForResult(Intent.createChooser(intent, "Выберите файл"), REQUEST_PICK_FILE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { handleFileSelection(it) }
        }
    }

    private fun handleFileSelection(uri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                if (!authenticateSocket()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ChatActivity, R.string.error_auth_socket, Toast.LENGTH_LONG).show()
                    }
                    return@launch
                }
                val fileName = getFileName(uri)
                val fileSize = getFileSize(uri)
                val uploadData = JSONObject().apply {
                    put("recipient_id", recipientId)
                    put("filename", fileName)
                    put("size", fileSize)
                }
                SocketManager.sendLine("UPLOAD_FILE:$uploadData")
                val response = SocketManager.readLine()
                if (response == "READY_FOR_FILE") {
                    contentResolver.openInputStream(uri)?.use { input ->
                        val socket = SocketManager.getSocket()
                        socket?.getOutputStream()?.let { output ->
                            val buffer = ByteArray(8192)
                            var bytesRead: Int
                            while (input.read(buffer).also { bytesRead = it } != -1) {
                                output.write(buffer, 0, bytesRead)
                            }
                            output.flush()
                        }
                    }
                    val uploadResponse = SocketManager.readLine()
                    if (uploadResponse?.startsWith("FILE_UPLOADED:") == true) {
                        val msgId = uploadResponse.substringAfter("FILE_UPLOADED:").toLongOrNull() ?: 0L
                        val msg = MessageEntity(
                            id = msgId,
                            senderId = currentUserId,
                            recipientId = recipientId,
                            groupId = null,
                            text = "[File: $fileName]",
                            filePath = fileName,
                            timestamp = System.currentTimeMillis(),
                            isRead = false
                        )
                        MessageRepository.get().insertLocal(msg)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ChatActivity, "Файл отправлен: $fileName", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, "Ошибка файла: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } finally {
                SocketManager.disconnect()
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var name = "unknown"
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex >= 0) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }

    private fun getFileSize(uri: Uri): Long {
        var size = 0L
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex >= 0) {
                size = cursor.getLong(sizeIndex)
            }
        }
        return size
    }

    override fun onDestroy() {
        super.onDestroy()
        isListening = false
    }
}
