package com.example.kotlincats.di

import android.app.Application
import androidx.room.Room
import com.example.kotlincats.api.UserServiceApi
import com.example.kotlincats.model.database.UserDao
import com.example.kotlincats.model.database.UserDatabase
import com.example.kotlincats.model.database.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(
            application,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, userServiceApi: UserServiceApi): UserRepository {
        return UserRepository(userDao, userServiceApi)
    }
}