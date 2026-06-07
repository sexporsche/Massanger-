package com.example.messenger.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.entities.MessageEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter(
    private val currentUserId: Int
) : ListAdapter<MessageEntity, ChatAdapter.MessageViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val TAG = "MessengerChatAdapter"
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.senderId == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_SENT) {
            R.layout.item_message_sent
        } else {
            R.layout.item_message_received
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, position)
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessageText: TextView = itemView.findViewById(R.id.tvMessageText)
        private val tvMessageTime: TextView = itemView.findViewById(R.id.tvMessageTime)
        private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(message: MessageEntity, position: Int) {
            tvMessageText.text = message.text ?: message.filePath ?: ""
            tvMessageTime.text = dateFormat.format(Date(message.timestamp))
            Log.v(TAG, "bind pos=$position id=${message.id} sender=${message.senderId} textLen=${message.text?.length}")
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem == newItem
    }
}
