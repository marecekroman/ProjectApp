package cz.utb.fai.projectapp.repository

import android.util.Log

import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import cz.utb.fai.projectapp.api.ChatGPTNetwork.Message
import cz.utb.fai.projectapp.api.ChatGPTService

class ChatRepository(private val apiService: ChatGPTService) {

    private val CHATGPTMODEL = "gpt-3.5-turbo"
    suspend fun chatCompletion(message:String) : ChatResponse? {
            val chatRequest = ChatRequest(
                arrayListOf(
                    Message("Start chat",
                        "system"),
                    Message(
                        message,
                        "user")
                ),
                CHATGPTMODEL
            )
            val response = apiService.chatCompletion(chatRequest)
            if(response.isSuccessful) {
                return response.body()
            } else {
                Log.d("Error: ", response.errorBody().toString())
                return null
            }
    }
}