package cz.utb.fai.projectapp.api

import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
interface ChatGPTService {

   @POST("chat/completions")
   suspend fun chatCompletion(
      @Body chatRequest: ChatRequest,
      @Header("Content-Type") contentType : String = "application/json",
      @Header("Authorization") authorization : String
      ): Response<ChatResponse>
}