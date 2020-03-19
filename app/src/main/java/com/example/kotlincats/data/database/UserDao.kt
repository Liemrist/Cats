package com.example.kotlincats.database

import androidx.room.*
import com.example.kotlincats.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * from user_table ORDER BY photoUrl ASC")
    suspend fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<User>)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun delete(user: User)
}