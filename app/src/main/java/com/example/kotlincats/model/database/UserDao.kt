package com.example.kotlincats.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    // FIXME: If it's running on a separate thread, why it's not used with coroutines?
    @Query("SELECT * from user_table ORDER BY photoUrl ASC")
    fun getAlphabetizedUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

//    @Transaction
//    open fun updateData(users: List<User>) {
//        deleteAllUsers()
//        insertAll(users)
//    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<User>)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}