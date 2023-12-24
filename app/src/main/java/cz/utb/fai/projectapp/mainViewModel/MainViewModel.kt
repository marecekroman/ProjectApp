package cz.utb.fai.projectapp.mainViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.projectapp.api.ChatGPTNetwork.Message
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
    val showLoading = MutableLiveData<Boolean>()
    val processToSettings = MutableLiveData<Boolean>()
    var responseMutable = MutableLiveData<String>()

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

    fun chatCompletion(){
        val question = questionMutable.value
        if (!question.isNullOrEmpty()) {
            viewModelScope.launch {
                val response = repository.chatCompletion(question.trim())
                    ?.choices
                    ?.first()
                    ?.message
                    ?.content.toString().trim()
                response?.let {
                    responseMutable.postValue(it)
                    insertMessage(MessageEntity(text = question, isSender = true, timestamp = System.currentTimeMillis()))
                    insertMessage(MessageEntity(text = it, isSender = false, timestamp = System.currentTimeMillis()))
                    responseMutable.postValue(it)
                    Log.d("Error", it)
                }
                questionMutable.postValue("")
            }
        } else {
            showLoading.value = true
        }
    }

    fun waitForResponse() {
        showLoading.value = false
    }
}
