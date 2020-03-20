package com.example.kotlincats.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlincats.domain.model.Cat


@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {

    abstract fun userDao(): CatDao
}
