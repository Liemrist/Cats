package com.example.kotlincats.data.database

import androidx.room.*
import com.example.kotlincats.data.models.CatDatabaseModel

@Dao
interface CatDao {

    @Query("SELECT * from cat_table ORDER BY photoUrl ASC")
    suspend fun getCats(): List<CatDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cat: CatDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<CatDatabaseModel>)

    @Query("DELETE FROM cat_table")
    suspend fun deleteAllCats()

    @Delete
    suspend fun delete(cat: CatDatabaseModel)
}