package cz.utb.fai.projectapp.api

import cz.utb.fai.projectapp.api.ChatResponse.ChatRequest
import cz.utb.fai.projectapp.api.ChatResponse.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
interface ChatGPTService {

    @POST("chat/completions")
    fun chatCompletion(
        @Body chatRequest: ChatRequest,
        @Header("Content-Type") contentType : String = "application/json",
        @Header("Authorization") authorization : String = "Bearer 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"
    ) : Call<ChatResponse>
}