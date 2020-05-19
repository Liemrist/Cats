package com.example.kotlincats.di

import android.content.Context
import com.example.kotlincats.di.viewModel.ViewModelModule
import com.example.kotlincats.presentation.catList.CatListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(target: CatListFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }
}