package cz.utb.fai.projectapp.repository

import android.content.Context
import android.util.Log
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import cz.utb.fai.projectapp.api.ChatGPTNetwork.Message
import cz.utb.fai.projectapp.api.ChatGPTService
import cz.utb.fai.projectapp.utility.SecurePreferences
import kotlin.Result

// This class is responsible for handling chat operations
class ChatRepository(private val apiService: ChatGPTService, private val context: Context) {

    // This function completes a chat message
    suspend fun chatCompletion(message: String): Result<ChatResponse?> {

        // Get the model from SecurePreferences and convert it to lowercase
        val selectedModel = SecurePreferences.getModel(context).lowercase()

        // Get the API key from SecurePreferences and remove any leading or trailing whitespace
        val apiKey = SecurePreferences.getApiKey(context)?.trim()

        if (apiKey.isNullOrEmpty()) {

            // Check if the API key is empty or null
            Log.e("ChatRepository", "API key is empty or null.")
            return Result.failure(IllegalArgumentException("API key is not set."))
        }

        // Create a chat request with initial messages
        val chatRequest = ChatRequest(
            arrayListOf(
                // System starts the chat
                Message("Start chat",
                    "system"),
                // User sends a message
                Message(
                    message,
                    "user")
            ),
            // Select a model for the chat
            model = selectedModel
        )

        // Send the chat request to the API service
        val response = apiService.chatCompletion(chatRequest, authorization = "Bearer $apiKey")

        // Check if the response is successful
        return if (response.isSuccessful) {

            // If successful, return the response body
            Result.success(response.body())
        } else {

            // If not successful, determine the error message
            val errorMessage = when (response.code()) {

                // If the response code is 401 or 403, the API key is invalid
                401, 403 -> "Invalid API key: ${response.code()}"

                // Otherwise, return the error body
                else -> "Error: ${response.errorBody()?.string()}"
            }

            // Log the error message
            Log.e("ChatRepository", errorMessage)

            // Return a failure with the error message
            Result.failure(Exception(errorMessage))
        }
    }
}