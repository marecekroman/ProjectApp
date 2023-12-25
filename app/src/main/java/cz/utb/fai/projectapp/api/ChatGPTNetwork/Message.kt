package cz.utb.fai.projectapp.api.ChatGPTNetwork

data class Message(

    // This is the content of the message
    val content: String,

    // This is the role of the user who sent the message
    val role: String
)