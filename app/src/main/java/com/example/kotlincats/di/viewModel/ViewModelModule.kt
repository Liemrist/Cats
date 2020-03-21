package com.example.kotlincats.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincats.di.viewModel.ViewModelKey
import com.example.kotlincats.presentation.list.CatListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CatListViewModel::class)
    abstract fun bindUserViewModel(catListViewModel: CatListViewModel): ViewModel
}


