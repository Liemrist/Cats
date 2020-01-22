package com.example.kotlincats.model.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    val users: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application, viewModelScope).userDao()

        repository = UserRepository(userDao)
        users = repository.allUsers
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun insert(users: List<User>) = viewModelScope.launch {
        repository.insert(users)
    }
}