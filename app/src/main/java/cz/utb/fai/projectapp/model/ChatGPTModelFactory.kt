package cz.utb.fai.projectapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.projectapp.database.AppDatabase
import cz.utb.fai.projectapp.viewModel.MainViewModel
import cz.utb.fai.projectapp.repository.ChatRepository

@Suppress("UNCHECKED_CAST")
class ChatGPTModelFactory(
    private val repository: ChatRepository,
    private val database: AppDatabase
) : ViewModelProvider.Factory  {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(repository, database) as T
            }
            throw IllegalArgumentException("Wrong ViewModel class")
        }
}