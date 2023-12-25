package cz.utb.fai.projectapp.api

import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatRequest
import cz.utb.fai.projectapp.api.ChatGPTNetwork.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/* This is an interface for the ChatGPTService */
interface ChatGPTService {
   /* This is a POST request to the chat/completions endpoint */
   @POST("chat/completions")
   /* This is a suspend function named chatCompletion */

   suspend fun chatCompletion(
      /* This is a parameter of type ChatRequest named chatRequest */
      @Body chatRequest: ChatRequest,
      /* This is a parameter of type String named contentType with a default value of "application/json" */
      @Header("Content-Type") contentType : String = "application/json",
      /* This is a parameter of type String named authorization */
      @Header("Authorization") authorization : String
      ): Response<ChatResponse>
}