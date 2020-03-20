package com.example.kotlincats.presentation.list

import androidx.lifecycle.*
import com.example.kotlincats.domain.model.Cat
import com.example.kotlincats.data.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {


    private val catsMutableLiveData = MutableLiveData<List<Cat>>()
    private val isProgressBarVisibleMutableLiveData = MutableLiveData<Boolean>()

    var cats: LiveData<List<Cat>> = catsMutableLiveData
    val isProgressBarVisible: LiveData<Boolean> = isProgressBarVisibleMutableLiveData


    fun loadCats() {
        viewModelScope.launch  {
            try {
                isProgressBarVisibleMutableLiveData.value = true
                val catsFromRepository = withContext(Dispatchers.IO) {
                    catRepository.getCats()
                }

                catsMutableLiveData.value = catsFromRepository
            } finally {
                isProgressBarVisibleMutableLiveData.value = false
            }
        }
    }


    fun delete(cat: Cat) = viewModelScope.launch {
        catRepository.delete(cat)
    }


    fun insert(cat: Cat) = viewModelScope.launch {
        catRepository.insert(cat)
    }


    fun insert(cats: List<Cat>) = viewModelScope.launch {
        catRepository.insert(cats)
    }
}