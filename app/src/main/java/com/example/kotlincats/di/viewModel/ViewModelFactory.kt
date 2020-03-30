package com.example.kotlincats.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModelProvider: Provider<ViewModel> = viewModels[modelClass]
            ?: throw IllegalArgumentException("model class $modelClass not found")

        @Suppress("UNCHECKED_CAST")
        return viewModelProvider.get() as T
    }
}