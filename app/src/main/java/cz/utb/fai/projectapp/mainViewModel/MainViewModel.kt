package cz.utb.fai.projectapp.mainViewModel

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
    val showLoading = MutableLiveData<Boolean>()
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
                responseMutable.postValue(repository.chatCompletion(questionMutable.value.toString())
                    ?.choices
                    ?.first()
                    ?.message
                    ?.content.toString())
            }
        } else {
            //
            showLoading.value = true
        }
    }

    fun waitForResponse () {
        showLoading.value = false
    }
}