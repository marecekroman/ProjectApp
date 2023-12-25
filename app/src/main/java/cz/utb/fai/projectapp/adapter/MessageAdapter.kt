package cz.utb.fai.projectapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.projectapp.databinding.ReceivedMessageItemBinding
import cz.utb.fai.projectapp.databinding.SentMessageItemBinding
import cz.utb.fai.projectapp.model.MessageEntity

/* This is a class declaration for MessageAdapter. It takes a private variable 'messages' of type List<MessageEntity> as a parameter.
    The class extends RecyclerView.Adapter with a generic parameter of RecyclerView.ViewHolder. */
class MessageAdapter(private var messages: List<MessageEntity>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // This is a function named 'updateData' which takes a list of 'MessageEntity' as an argument named 'newMessages'
    fun updateData(newMessages: List<MessageEntity>) {

        messages = newMessages
        notifyDataSetChanged()
    }

    // Override the onCreateViewHolder function from the RecyclerView.Adapter class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // Create a LayoutInflater from the parent's context
        val inflater = LayoutInflater.from(parent.context)

        // Return a ViewHolder based on the viewType
        return when (viewType) {

            // If the viewType is VIEW_TYPE_SENT, return a SentMessageViewHolder
            VIEW_TYPE_SENT -> {

                // Inflate the SentMessageItemBinding layout
                val binding = SentMessageItemBinding.inflate(inflater, parent, false)

                // Return a new SentMessageViewHolder with the inflated layout
                SentMessageViewHolder(binding)
            }
            // If the viewType is anything else, return a ReceivedMessageViewHolder
            else -> {

                // Inflate the ReceivedMessageItemBinding layout
                val binding = ReceivedMessageItemBinding.inflate(inflater, parent, false)

                // Return a new ReceivedMessageViewHolder with the inflated layout
                ReceivedMessageViewHolder(binding)
            }
        }
    }

    // Override the onBindViewHolder function from the RecyclerView.Adapter class
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // Get the message at the given position
        val message = messages[position]

        // Bind the message to the holder
        when (holder) {

            // If the holder is a SentMessageViewHolder, bind the message to it
            is SentMessageViewHolder -> holder.bind(message)

            // If the holder is a ReceivedMessageViewHolder, bind the message to it
            is ReceivedMessageViewHolder -> holder.bind(message)
        }
    }

    // Override the getItemViewType function from the RecyclerView.Adapter class
    override fun getItemViewType(position: Int): Int {

        // Return VIEW_TYPE_SENT if the message at the given position was sent by the user, otherwise return VIEW_TYPE_RECEIVED
        return if (messages[position].isSender) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    // Override the getItemCount function from the RecyclerView.Adapter class
    override fun getItemCount() = messages.size

    // Define the SentMessageViewHolder class
    class SentMessageViewHolder(private val binding: SentMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Define a function to bind a message to the ViewHolder
        fun bind(message: MessageEntity) {

            // Set the messageSent property of the binding to the given message
            binding.messageSent = message

            // Execute any pending bindings
            binding.executePendingBindings()
        }
    }

    // Define the ReceivedMessageViewHolder class
    class ReceivedMessageViewHolder(private val binding: ReceivedMessageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Define a function to bind a message to the ViewHolder
        fun bind(message: MessageEntity) {

            // Set the messageReceived property of the binding to the given message
            binding.messageReceived = message

            // Execute any pending bindings
            binding.executePendingBindings()
        }
    }

    // Define a companion object to hold constants
    companion object {

        // Define a constant for the sent message view type
        const val VIEW_TYPE_SENT = 1

        // Define a constant for the received message view type
        const val VIEW_TYPE_RECEIVED = 2
    }
}