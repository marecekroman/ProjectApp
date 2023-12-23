package cz.utb.fai.projectapp.view

import android.app.Application
import cz.utb.fai.projectapp.api.ChatGPTService
import cz.utb.fai.projectapp.repository.ChatRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatGPTApplication : Application() {

    private val BASE_URL = "https://api.openai.com/v1/"
    val apiService: ChatGPTService by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON serialization/deserialization
            .build()

        retrofit.create(ChatGPTService::class.java)
    }

    val repository: ChatRepository by lazy {
        ChatRepository(apiService)
    }

    override fun onCreate() {
        super.onCreate()
    }
}