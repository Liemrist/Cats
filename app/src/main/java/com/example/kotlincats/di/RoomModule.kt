package com.example.kotlincats.di

import android.app.Application
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
    fun provideDatabase(application: Application): CatDatabase {
        return Room.databaseBuilder(
            application,
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