package com.example.messenger.data

import android.content.Context
import android.util.Log
import com.example.messenger.data.entities.MessageEntity
import kotlinx.coroutines.flow.Flow

class MessageRepository private constructor(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private var incomingListener: ((MessageEntity) -> Unit)? = null

    fun setIncomingListener(cb: (MessageEntity) -> Unit) {
        incomingListener = cb
    }

    fun conversationFlow(me: Int, peer: Int): Flow<List<MessageEntity>> {
        Log.d(TAG, "conversationFlow(me=$me, peer=$peer)")
        return db.messageDao().getConversationFlow(me, peer)
    }

    fun groupConversationFlow(groupKey: Int): Flow<List<MessageEntity>> {
        Log.d(TAG, "groupConversationFlow(groupKey=$groupKey)")
        return db.messageDao().observeGroupMessages(groupKey)
    }

    suspend fun insertLocal(msg: MessageEntity) {
        val row = db.messageDao().insert(msg)
        Log.d(TAG, "insertLocal id=${msg.id} sender=${msg.senderId} recipient=${msg.recipientId} row=$row text=${msg.text?.take(40)}")
    }

    suspend fun insertAllLocal(msgs: List<MessageEntity>) {
        if (msgs.isEmpty()) return
        db.messageDao().insertAll(msgs)
        Log.d(TAG, "insertAllLocal count=${msgs.size} maxId=${msgs.maxOfOrNull { it.id }}")
    }

    companion object {
        private const val TAG = "MessengerRepo"

        @Volatile private var INSTANCE: MessageRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) INSTANCE = MessageRepository(context.applicationContext)
                }
            }
        }

        fun get(): MessageRepository {
            return INSTANCE ?: throw IllegalStateException("MessageRepository not initialized. Call init(context) first")
        }
    }
}
