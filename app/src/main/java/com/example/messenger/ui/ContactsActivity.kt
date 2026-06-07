package com.example.messenger.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.entities.UserEntity
import com.example.messenger.databinding.ActivityContactsBinding
import com.example.messenger.network.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsAdapter
    private var currentUserId: Int = 0
    private var currentUsername: String = ""
    private var allUsers: List<UserEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MessageRepository.init(applicationContext)

        currentUserId = intent.getIntExtra("userId", 0)
        currentUsername = intent.getStringExtra("username").orEmpty()

        adapter = ContactsAdapter { user ->
            startActivity(
                Intent(this, ChatActivity::class.java).apply {
                    putExtra("userId", user.id)
                    putExtra("username", user.username)
                    putExtra("currentUserId", currentUserId)
                    putExtra("currentUsername", currentUsername)
                    putExtra("isOnline", user.isOnline)
                }
            )
        }

        binding.rvContacts.adapter = adapter
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.setHasFixedSize(true)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterContacts(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.swipeRefresh.setColorSchemeResources(R.color.md_primary)
        binding.swipeRefresh.setOnRefreshListener { loadContactsFromServer() }

        loadContactsFromServer()
    }

    private fun filterContacts(query: String) {
        val q = query.trim().lowercase()
        val filtered = if (q.isEmpty()) allUsers else allUsers.filter { it.username.lowercase().contains(q) }
        android.util.Log.d(TAG, "filterContacts q='$q' -> ${filtered.size} items (all=${allUsers.size})")
        adapter.submitList(filtered) {
            binding.tvContactsEmpty.isVisible = filtered.isEmpty()
        }
    }

    private fun loadContactsFromServer() {
        binding.swipeRefresh.isRefreshing = true
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val host = getString(R.string.server_host)
                val port = resources.getInteger(R.integer.server_port)
                SocketManager.connect(applicationContext, host, port)
                SocketManager.sendLine("LIST_USERS")
                val response = SocketManager.readLine()
                android.util.Log.d(TAG, "LIST_USERS raw length=${response?.length} preview=${response?.take(120)}")

                if (response?.startsWith("USERS:") == true) {
                    val jsonStr = response.substringAfter("USERS:")
                    val jsonArray = JSONArray(jsonStr)
                    val users = mutableListOf<UserEntity>()
                    for (i in 0 until jsonArray.length()) {
                        val userObj = jsonArray.getJSONObject(i)
                        val userId = userObj.getInt("id")
                        val username = userObj.getString("username")
                        val isOnline = userObj.optBoolean("isOnline", false)
                        if (userId != currentUserId) {
                            users.add(UserEntity(id = userId, username = username, isOnline = isOnline))
                        }
                    }
                    val db = AppDatabase.getInstance(applicationContext)
                    db.userDao().insertAll(users)

                    withContext(Dispatchers.Main) {
                        allUsers = users.sortedBy { it.username.lowercase() }
                        filterContacts(binding.etSearch.text?.toString().orEmpty())
                    }
                    android.util.Log.d(TAG, "Contacts loaded count=${users.size}")
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ContactsActivity, "Ошибка списка контактов", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "loadContacts", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ContactsActivity, "Сеть: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } finally {
                SocketManager.disconnect()
                withContext(Dispatchers.Main) {
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    companion object {
        private const val TAG = "MessengerContacts"
    }
}
