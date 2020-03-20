package com.example.kotlincats.data.database

import androidx.room.*
import com.example.kotlincats.domain.model.Cat

@Dao
interface CatDao {

    @Query("SELECT * from cat_table ORDER BY photoUrl ASC")
    suspend fun getUsers(): List<Cat>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<Cat>)

    @Query("DELETE FROM cat_table")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun delete(cat: Cat)
}