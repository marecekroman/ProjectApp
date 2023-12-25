package cz.utb.fai.projectapp.repository

import android.util.Log
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import cz.utb.fai.projectapp.api.ChatGPTNetwork.Message
import cz.utb.fai.projectapp.api.ChatGPTService
import kotlin.Result

class ChatRepository(private val apiService: ChatGPTService) {

    private val CHATGPTMODEL = "gpt-3.5-turbo"
    suspend fun chatCompletion(message: String, apiKey: String): Result<ChatResponse?> {
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
        val response = apiService.chatCompletion(chatRequest, authorization = "Bearer $apiKey")

        return if (response.isSuccessful) {
            Result.success(response.body())
        } else {
            val errorMessage = when (response.code()) {
                401, 403 -> "Invalid API key: ${response.code()}"
                else -> "Error: ${response.errorBody()?.string()}"
            }
            Log.e("ChatRepository", errorMessage)
            Result.failure(Exception(errorMessage))
        }
    }
}