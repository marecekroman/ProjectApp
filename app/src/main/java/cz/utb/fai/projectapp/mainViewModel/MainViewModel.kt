package cz.utb.fai.projectapp.mainViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _isReady = MutableStateFlow(false)

    val processToSettings = MutableLiveData<Boolean>()

    val isReady = _isReady.asStateFlow()
    init {
        viewModelScope.launch {
            delay(2500L)
            _isReady.value = true
        }
    }
    fun toSettings () {
        processToSettings.value = true
    }
}