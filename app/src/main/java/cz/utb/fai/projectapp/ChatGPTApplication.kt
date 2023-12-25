package cz.utb.fai.projectapp

import android.app.Application
import androidx.room.Room
import cz.utb.fai.projectapp.api.ChatGPTService
import cz.utb.fai.projectapp.database.AppDatabase
import cz.utb.fai.projectapp.model.ChatGPTModelFactory
import cz.utb.fai.projectapp.repository.ChatRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Define a class that extends the Application class
class ChatGPTApplication : Application() {

    // Define the base URL for the API
    private val BASE_URL = "https://api.openai.com/v1/"
    // Define a lazy-initialized variable for the API service
    val apiService: ChatGPTService by lazy {

        // Create a Retrofit builder
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL for the API
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