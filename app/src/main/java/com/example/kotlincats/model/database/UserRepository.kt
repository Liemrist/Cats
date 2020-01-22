package com.example.kotlincats.model.database

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allUsers: LiveData<List<User>> = userDao.getAlphabetizedUsers()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun insert(users: List<User>) {
        userDao.insert(users)
    }
}