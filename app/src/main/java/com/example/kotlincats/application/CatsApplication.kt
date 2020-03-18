package com.example.kotlincats.application

import android.app.Application
import com.example.kotlincats.di.AppComponent
import com.example.kotlincats.di.AppModule
import com.example.kotlincats.di.DaggerAppComponent

class CatsApplication : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = initializeComponent()
    }


    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}