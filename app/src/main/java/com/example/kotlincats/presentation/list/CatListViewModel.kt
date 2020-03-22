package com.example.kotlincats.presentation.list

import androidx.lifecycle.*
import com.example.kotlincats.domain.model.Cat
import com.example.kotlincats.data.CatRepository
import com.example.kotlincats.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository
) : ViewModel() {


    private val _cats = MutableLiveData<List<Cat>>()
    private val _isProgressBarVisible = MutableLiveData<Boolean>()
    private val _handleLoadMore = MutableLiveData<Event<Boolean>>()

    val cats: LiveData<List<Cat>> get() = _cats
    val isProgressBarVisible: LiveData<Boolean> get() = _isProgressBarVisible
    val handleLoadMore : LiveData<Event<Boolean>> get() = _handleLoadMore


    fun loadCats() {
        viewModelScope.launch  {
            try {
                _isProgressBarVisible.value = true
                val catsFromRepository = withContext(Dispatchers.IO) {
                    catRepository.getCats(15)
                }

                _cats.value = catsFromRepository
            } finally {
                _isProgressBarVisible.value = false
            }
        }
    }

    fun loadMoreCats() {
        viewModelScope.launch {
            val catsFromRepository = withContext(Dispatchers.IO) {
                catRepository.getMoreCats(10)
            }

            val moreCats = _cats.value?.plus(catsFromRepository)
            _cats.value = moreCats

            _handleLoadMore.value = Event(true)  // Trigger the event by setting a new Event as a new value
        }
    }

    fun delete(cat: Cat) = viewModelScope.launch {
        catRepository.delete(cat)
    }
}