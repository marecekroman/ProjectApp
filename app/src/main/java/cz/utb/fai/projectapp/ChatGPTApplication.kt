package cz.utb.fai.projectapp

import android.app.Application
import androidx.room.Room
import cz.utb.fai.projectapp.api.ChatGPTService
import cz.utb.fai.projectapp.database.AppDatabase
import cz.utb.fai.projectapp.model.ChatGPTModelFactory
import cz.utb.fai.projectapp.repository.ChatRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Define a class that extends the Application class
class ChatGPTApplication : Application() {

    // Define the base URL for the API
    private val BASE_URL = "https://api.openai.com/v1/"

    // Create a new OkHttpClient instance
    val okHttpClient = OkHttpClient.Builder()
        // Set the connection timeout to 30 seconds
        .connectTimeout(30, TimeUnit.SECONDS)
        // Set the read timeout to 30 seconds
        .readTimeout(30, TimeUnit.SECONDS)
        // Set the write timeout to 30 seconds
        .writeTimeout(30, TimeUnit.SECONDS)
        // Build the OkHttpClient instance
        .build()

    // Define a lazy-initialized variable for the API service
    val apiService: ChatGPTService by lazy {

        // Create a Retrofit builder
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL for the API
            .client(okHttpClient) // This line sets the client for the request to be the okHttpClient
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON serialization/deserialization
            .build() // Build the Retrofit instance

        retrofit.create(ChatGPTService::class.java) // Create the API service
    }

    // Define a lazy-initialized variable for the database
    val database: AppDatabase by lazy {
        Room.databaseBuilder( // Create a Room database builder
            applicationContext, // Use the application context
            AppDatabase::class.java, "message-database" // Set the database class and name
        ).build() // Build the database
    }

    // Define a lazy-initialized variable for the repository
    val repository: ChatRepository by lazy {
        ChatRepository(apiService, applicationContext) // Create the repository with the API service and application context
    }

    // Define a function to provide the ViewModel factory
    fun provideViewModelFactory(): ChatGPTModelFactory {
        return ChatGPTModelFactory(repository, database) // Return a new ViewModel factory with the repository and database
    }
}