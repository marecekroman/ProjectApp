package cz.utb.fai.projectapp.api.ChatResponse

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)