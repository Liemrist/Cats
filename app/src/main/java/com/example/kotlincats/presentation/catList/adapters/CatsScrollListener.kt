package com.example.kotlincats.presentation.catList.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatsScrollListener(private val listener: LoadMoreListener)
    : RecyclerView.OnScrollListener() {


    private var isLoading: Boolean = false


    fun setLoaded() {
        isLoading = false
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0

        val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (!isLoading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
            listener.onScrolledToBottom()
            isLoading = true
        }
    }


    interface LoadMoreListener {
        fun onScrolledToBottom()
    }


    companion object {
        private const val VISIBLE_THRESHOLD = 3 // Progress bar will appear when user sees the 3rd item from the end.
    }
}