package com.example.kotlincats.presentation.catList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincats.domain.models.Cat
import com.example.kotlincats.domain.usecases.DeleteCatUseCase
import com.example.kotlincats.domain.usecases.GetCatsUseCase
import com.example.kotlincats.domain.usecases.SaveCatsUseCase
import com.example.kotlincats.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatListViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val saveCatsUseCase: SaveCatsUseCase,
    private val deleteCatUseCase: DeleteCatUseCase
) : ViewModel() {

    private val _cats = MutableLiveData<List<Cat>>()
    private val _isProgressBarVisible = MutableLiveData<Event<Boolean>>()
    private val _handleLoadMore = MutableLiveData<Event<Boolean>>()

    val cats: LiveData<List<Cat>> get() = _cats
    val isProgressBarVisible: LiveData<Event<Boolean>> get() = _isProgressBarVisible
    val handleLoadMore: LiveData<Event<Boolean>> get() = _handleLoadMore


    fun initCats() {
        if (_cats.value !== null) return

        viewModelScope.launch {
            try {
                _isProgressBarVisible.value = Event(true)
                val catsFromRepository = withContext(Dispatchers.IO) {
                    getCatsUseCase.execute(ITEMS_NUMBER)
                }

                withContext(Dispatchers.IO) {
                    saveCatsUseCase.execute(catsFromRepository)
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
                getCatsUseCase.execute(ITEMS_NUMBER)
            }

            val moreCats = _cats.value?.plus(catsFromRepository)
            _cats.value = moreCats

            _handleLoadMore.value = Event(true)  // Trigger the event by setting a new Event as a new value
        }
    }


    fun delete(cat: Cat) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            deleteCatUseCase.execute(cat)
        }
    }


    companion object {
        private const val ITEMS_NUMBER = 10
    }
}