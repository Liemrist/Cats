package com.example.kotlincats.di

import com.example.kotlincats.list.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RoomModule::class
    ]
)
interface AppComponent {

    fun inject(target: UserViewModel)
}