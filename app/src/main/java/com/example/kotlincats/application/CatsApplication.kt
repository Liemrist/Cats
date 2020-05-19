package com.example.kotlincats.application

import android.app.Application
import com.example.kotlincats.di.AppComponent
import com.example.kotlincats.di.DaggerAppComponent

class CatsApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContext(this).build()
    }
}