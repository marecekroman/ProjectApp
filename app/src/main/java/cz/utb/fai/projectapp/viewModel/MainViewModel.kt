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

class MainViewModel(
    private val repository: ChatRepository,
    private val database: AppDatabase // Pass the database instance
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    val questionMutable = MutableLiveData<String>()
    private val _apiError = MutableLiveData<String>()
    val apiError: LiveData<String> = _apiError
    val processToSettings = MutableLiveData<Boolean>()

    // Accessing the DAO to get all messages
    val allMessages: LiveData<List<MessageEntity>> = database.messageDao().getAllMessages()

    fun insertMessage(message: MessageEntity) {
        viewModelScope.launch {
            database.messageDao().insert(message)
        }
    }

    init {
        viewModelScope.launch {
            delay(2500L)
            _isReady.value = true
        }
    }

    fun toSettings() {
        processToSettings.value = true
    }

    fun chatCompletion(context: Context) {
        val question = questionMutable.value
        if (!question.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = repository.chatCompletion(question.trim())
                result.onSuccess { chatResponse ->
                    chatResponse?.choices?.firstOrNull()?.message?.content?.let { response ->
                        insertMessage(MessageEntity(text = question, isSender = true, timestamp = System.currentTimeMillis()))
                        insertMessage(MessageEntity(text = response, isSender = false, timestamp = System.currentTimeMillis()))
                    }
                }.onFailure { exception ->
                    _apiError.postValue(exception.message)
                }

                questionMutable.postValue("")
            }
        } else {
            Toast.makeText(context, "Type question", Toast.LENGTH_SHORT).show()
        }
    }
}
