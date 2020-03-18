package com.example.kotlincats.repository

import com.example.kotlincats.api.catApi.CatApi
import com.example.kotlincats.api.hipsterIpsumApi.HipsterIpsumApi
import com.example.kotlincats.database.UserDao
import com.example.kotlincats.model.User
import com.example.kotlincats.utils.Mappers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val catApi: CatApi,
    private val hipsterIpsumApi: HipsterIpsumApi
) {

    suspend fun getUsers(): List<User> {
        var users: List<User> = userDao.getUsers()
        // FIXME: DB is not populated here yet after storage clean.

        if (users.isEmpty()) {
            val cats = catApi.getCats(30)
            val text = hipsterIpsumApi.getParagraphs(30);

            users = Mappers.mapCatsWithTextToUsers(cats, text)
            insert(users)
        }

        return users
    }


    suspend fun insert(user: User) = userDao.insert(user)


    suspend fun insert(users: List<User>) = userDao.insert(users)


    suspend fun delete(user: User) = userDao.delete(user)
}