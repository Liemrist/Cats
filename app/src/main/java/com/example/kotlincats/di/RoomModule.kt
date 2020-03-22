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
            "cat_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(catDatabase: CatDatabase): CatDao {
        return catDatabase.userDao()
    }
}