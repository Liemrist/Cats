package com.example.kotlincats.di

import android.content.Context
import androidx.room.Room
import com.example.kotlincats.data.database.CatDao
import com.example.kotlincats.data.database.CatDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(applicationContext: Context): CatDatabase {
        return Room.databaseBuilder(
            applicationContext,
            CatDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(catDatabase: CatDatabase): CatDao {
        return catDatabase.userDao()
    }

    private companion object {
        private const val DATABASE_NAME = "cat_database"
    }
}