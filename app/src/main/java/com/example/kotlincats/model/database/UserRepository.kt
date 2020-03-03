package com.example.kotlincats.model.database

import com.example.kotlincats.api.CatDto
import com.example.kotlincats.api.UserServiceApi
import com.example.kotlincats.model.User

// FIXME: do I need an @Inject constructor here?
class UserRepository(private val userDao: UserDao, private val userServiceApi: UserServiceApi) {

    suspend fun getUsers(): List<User> {
        var users: List<User> = userDao.getUsers()
        // FIXME: DB is not populated here yet after storage clean.

        if (users.isEmpty()) {
            val cats = userServiceApi.getCats(30)
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