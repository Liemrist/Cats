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
    private val _isProgressBarVisible = MutableLiveData<Event<Boolean>>()
    private val _handleLoadMore = MutableLiveData<Event<Boolean>>()

    val cats: LiveData<List<Cat>> get() = _cats
    val isProgressBarVisible: LiveData<Event<Boolean>> get() = _isProgressBarVisible
    val handleLoadMore : LiveData<Event<Boolean>> get() = _handleLoadMore


    fun loadCats() {
        viewModelScope.launch  {
            try {
                _isProgressBarVisible.value = Event(true)
                val catsFromRepository = withContext(Dispatchers.IO) {
                    catRepository.getCats(INITIAL_ITEMS_NUMBER)
                }

                _cats.value = catsFromRepository
            } finally {
                _isProgressBarVisible.value = Event(false)
            }
        }
    }


    fun loadMoreCats() {
        viewModelScope.launch {
            val catsFromRepository = withContext(Dispatchers.IO) {
                catRepository.getMoreCats(ITEMS_NUMBER_MORE)
            }

            val moreCats = _cats.value?.plus(catsFromRepository)
            _cats.value = moreCats

            _handleLoadMore.value = Event(true)  // Trigger the event by setting a new Event as a new value
        }
    }


    fun delete(cat: Cat) = viewModelScope.launch {
        catRepository.delete(cat)
    }


    companion object {
        private const val INITIAL_ITEMS_NUMBER = 15
        private const val ITEMS_NUMBER_MORE = 10
    }
}