package com.example.kotlincats.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlincats.model.User
import com.example.kotlincats.database.UserDatabase
import com.example.kotlincats.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private val usersMutableLiveData = MutableLiveData<List<User>>()
    private val isProgressBarVisibleMutableLiveData = MutableLiveData<Boolean>()

    var users: LiveData<List<User>> = usersMutableLiveData
    val isProgressBarVisible: LiveData<Boolean> = isProgressBarVisibleMutableLiveData


    init {
        val userDao = UserDatabase.getDatabase(
            application,
            viewModelScope
        ).userDao()

        repository =
            UserRepository(userDao)
//        users = repository.allUsers
    }


    fun loadUsers() {
        viewModelScope.launch  {
            try {
                isProgressBarVisibleMutableLiveData.value = true
                val testUsers = withContext(Dispatchers.IO) {
                    repository.getUsers()
                }

                usersMutableLiveData.value = testUsers
            } finally {
                isProgressBarVisibleMutableLiveData.value = false
            }
        }
    }


    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
    }


    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }


    fun insert(users: List<User>) = viewModelScope.launch {
        repository.insert(users)
    }
}