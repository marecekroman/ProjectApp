package cz.utb.fai.projectapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.projectapp.database.AppDatabase
import cz.utb.fai.projectapp.model.MessageEntity
import cz.utb.fai.projectapp.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Declare ViewModel class with two private variables: repository of type ChatRepository and database of type AppDatabase
class MainViewModel(
    private val repository: ChatRepository,
    private val database: AppDatabase // Pass the database instance
) : ViewModel() {

    // Declare a private mutable state flow variable _isReady with initial value false
    private val _isReady = MutableStateFlow(false)
    // Declare a public state flow variable isReady that mirrors _isReady
    val isReady = _isReady.asStateFlow()
    // Declare a mutable live data variable questionMutable of type String
    val questionMutable = MutableLiveData<String>()
    // Declare a private mutable live data variable _apiError of type String
    private val _apiError = MutableLiveData<String>()
    // Declare a public live data variable apiError that mirrors _apiError
    val apiError: LiveData<String> = _apiError
    // Declare a mutable live data variable processToSettings of type Boolean
    val processToSettings = MutableLiveData<Boolean>()

    // Access the DAO to get all messages and assign it to the variable allMessages
    val allMessages: LiveData<List<MessageEntity>> = database.messageDao().getAllMessages()

    // Declare a function insertMessage that takes a MessageEntity object as parameter and inserts it into the database
    fun insertMessage(message: MessageEntity) {
        viewModelScope.launch {
            database.messageDao().insert(message)
        }
    }

    // Initialize the ViewModel
    init {
        viewModelScope.launch {
            // Delay the execution by 2500 milliseconds
            delay(2500L)
            // Set the value of _isReady to true
            _isReady.value = true
        }
    }

    // Declare a function toSettings that sets the value of processToSettings to true
    fun toSettings() {
        processToSettings.value = true
    }

    // Declare a function chatCompletion that takes a Context object as parameter and performs chat completion
    fun chatCompletion(context: Context) {
        // Get the value of questionMutable
        val question = questionMutable.value
        // Check if question is not null or empty
        if (!question.isNullOrEmpty()) {
            viewModelScope.launch {
                // Trim the question and pass it to the chatCompletion function of the repository
                val result = repository.chatCompletion(question.trim())
                // Handle the success case
                result.onSuccess { chatResponse ->
                    // Check if the first choice's message content is not null and insert it into the database
                    chatResponse?.choices?.firstOrNull()?.message?.content?.let { response ->
                        insertMessage(MessageEntity(text = question, isSender = true, timestamp = System.currentTimeMillis()))
                        insertMessage(MessageEntity(text = response, isSender = false, timestamp = System.currentTimeMillis()))
                    }
                // Handle the failure case
                }.onFailure { exception ->
                    // Post the exception message to _apiError
                    _apiError.postValue(exception.message)
                }

                // Reset the value of questionMutable
                questionMutable.postValue("")
            }
        } else {
            // Show a toast message if question is null or empty
            Toast.makeText(context, "Type question", Toast.LENGTH_SHORT).show()
        }
    }

    // Declare a function deleteAllMessages that deletes all messages from the database
    fun deleteAllMessages() {
        viewModelScope.launch {
            database.messageDao().deleteAllMessages()
        }
    }
}
