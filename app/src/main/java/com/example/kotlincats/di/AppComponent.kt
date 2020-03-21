package com.example.kotlincats.di

import com.example.kotlincats.di.viewModel.ViewModelModule
import com.example.kotlincats.presentation.list.CatListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RoomModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(target: CatListFragment)
}