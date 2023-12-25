package cz.utb.fai.projectapp.api.ChatGPTNetwork

data class ChatRequest(

    // A list of messages
    val messages: List<Message>,

    // The model used for the chat
    val model: String
)