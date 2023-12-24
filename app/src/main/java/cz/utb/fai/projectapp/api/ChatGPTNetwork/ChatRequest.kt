package cz.utb.fai.projectapp.api.ChatGPTNetwork

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)