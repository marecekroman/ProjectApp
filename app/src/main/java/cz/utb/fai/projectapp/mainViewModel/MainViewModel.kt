package cz.utb.fai.projectapp.mainViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.projectapp.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: ChatRepository
)  : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    val questionMutable = MutableLiveData<String>()
    val showHint = MutableLiveData<Boolean>()
    val processToSettings = MutableLiveData<Boolean>()
    var responseMutable = MutableLiveData<String>()


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
        if (questionMutable.value != null && !questionMutable.value!!.isEmpty()) {
            viewModelScope.launch {
                repository.chatCompletion(questionMutable.value.toString())
                responseMutable.value = repository.chatResponse.value.toString()
            }
            Log.d("Message: ", questionMutable.value.toString())
            
        } else {
            //
            showHint.value = true
        }
    }

    fun hideHintAndNotFound () {
        showHint.value = false
    }
}