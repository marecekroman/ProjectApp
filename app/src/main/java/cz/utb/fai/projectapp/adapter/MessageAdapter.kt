package cz.utb.fai.projectapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.projectapp.databinding.ReceivedMessageItemBinding
import cz.utb.fai.projectapp.databinding.SentMessageItemBinding
import cz.utb.fai.projectapp.model.MessageEntity

class MessageAdapter(private var messages: List<MessageEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateData(newMessages: List<MessageEntity>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_SENT -> {
                val binding = SentMessageItemBinding.inflate(inflater, parent, false)
                SentMessageViewHolder(binding)
            }
            else -> {
                val binding = ReceivedMessageItemBinding.inflate(inflater, parent, false)
                ReceivedMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is SentMessageViewHolder -> holder.bind(message)
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSender) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun getItemCount() = messages.size

    class SentMessageViewHolder(private val binding: SentMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageEntity) {
            binding.messageSent = message
            binding.executePendingBindings()
        }
    }

    class ReceivedMessageViewHolder(private val binding: ReceivedMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageEntity) {
            binding.messageReceived = message
            binding.executePendingBindings()
        }
    }

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2
    }
}