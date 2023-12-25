package cz.utb.fai.projectapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.projectapp.database.AppDatabase
import cz.utb.fai.projectapp.viewModel.MainViewModel
import cz.utb.fai.projectapp.repository.ChatRepository

/* This is a class named ChatGPTModelFactory which takes two parameters: a repository of type ChatRepository and a database of type AppDatabase */
class ChatGPTModelFactory(
    private val repository: ChatRepository,
    private val database: AppDatabase
) : ViewModelProvider.Factory  {

        /* This is an overridden function named 'create' which takes a parameter modelClass of type Class<T> and returns an object of type T */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            /* Here we are checking if the modelClass is assignable from MainViewModel::class.java */

            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

                /* If the condition is true, we suppress the unchecked cast warning and return a new instance of MainViewModel with repository and database as parameters */
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository, database) as T
            }

            /* If the condition is false, we throw an IllegalArgumentException with the message 'Wrong ViewModel class' */
            throw IllegalArgumentException("Wrong ViewModel class")
        }
}