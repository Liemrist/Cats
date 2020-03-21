package com.example.kotlincats.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideApplication(): Context {
        return applicationContext
    }
}
