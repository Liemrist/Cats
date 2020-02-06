package com.example.kotlincats.repository

import android.util.Log
import com.example.kotlincats.api.CatDto
import com.example.kotlincats.database.UserDao
import com.example.kotlincats.model.User
import com.example.kotlincats.service.CatPhotos

class UserRepository(private val userDao: UserDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    //    val allUsers: LiveData<List<User>> = userDao.getAlphabetizedUsers()

    suspend fun getUsers(): List<User> {
        var users: List<User> = userDao.getUsers()
        // FIXME: DB is not populated here yet after storage clean.
        Log.d("NETWORK", "UserRepository getUsers: $users")
        if (users.isEmpty()) {
            val cats = CatPhotos.getCats(30)
            users = mapCatsToUsers(cats)

            insert(users)
        }

        return users
    }

    suspend fun insert(user: User) = userDao.insert(user)


    suspend fun insert(users: List<User>) = userDao.insert(users)


    suspend fun delete(user: User) = userDao.delete(user)


    private fun mapCatsToUsers(cats: List<CatDto>): List<User> {
        val users: ArrayList<User> = arrayListOf()

        for (cat in cats) {
            users.add(
                User(cat.hashCode(), cat.id, cat.imageUrl)
            )
        }

        return users
    }
}