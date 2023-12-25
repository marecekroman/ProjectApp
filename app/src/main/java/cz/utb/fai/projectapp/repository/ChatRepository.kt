package cz.utb.fai.projectapp.repository

import android.content.Context
import android.util.Log
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import cz.utb.fai.projectapp.api.ChatGPTNetwork.Message
import cz.utb.fai.projectapp.api.ChatGPTService
import cz.utb.fai.projectapp.utility.SecurePreferences
import kotlin.Result

class ChatRepository(private val apiService: ChatGPTService, private val context: Context) {
    suspend fun chatCompletion(message: String): Result<ChatResponse?> {
        val selectedModel = SecurePreferences.getModel(context).lowercase()
        val apiKey = SecurePreferences.getApiKey(context)?.trim()

        if (apiKey.isNullOrEmpty()) {
            Log.e("ChatRepository", "API key is empty or null.")
            return Result.failure(IllegalArgumentException("API key is not set."))
        }
            val chatRequest = ChatRequest(
                arrayListOf(
                    Message("Start chat",
                        "system"),
                    Message(
                        message,
                        "user")
                ),
                model = selectedModel
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