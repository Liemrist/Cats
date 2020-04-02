package com.example.kotlincats.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincats.data.CatRepository
import com.example.kotlincats.domain.model.Cat
import com.example.kotlincats.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    private val disposable: CompositeDisposable = CompositeDisposable()

    val cats: LiveData<List<Cat>> get() = _cats
    val isProgressBarVisible: LiveData<Event<Boolean>> get() = _isProgressBarVisible
    val handleLoadMore : LiveData<Event<Boolean>> get() = _handleLoadMore


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


    fun loadCats() {
        _isProgressBarVisible.value = Event(true)

        disposable.add(
            catRepository.getCats(INITIAL_ITEMS_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    Log.d("TAG", "HERE $result")
                    _cats.value = result
                }
        )

        _isProgressBarVisible.value = Event(false)
    }


    fun loadMoreCats() {
        // TODO:
//        viewModelScope.launch {
//            val catsFromRepository = withContext(Dispatchers.IO) {
//                catRepository.getMoreCats(ITEMS_NUMBER_MORE)
//            }
//
//            val moreCats = _cats.value?.plus(catsFromRepository)
//            _cats.value = moreCats
//
//            _handleLoadMore.value = Event(true)  // Trigger the event by setting a new Event as a new value
//        }
    }


    fun delete(cat: Cat) = viewModelScope.launch {
        catRepository.delete(cat)
    }


    companion object {
        private const val INITIAL_ITEMS_NUMBER = 15
        private const val ITEMS_NUMBER_MORE = 10
    }
}