package com.example.kotlincats.presentation.list.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CatsScrollListener : RecyclerView.OnScrollListener() {


    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var visibleThreshold = 3 // Progress bar will appear when user sees the 3rd item from the end.
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount:Int = 0


    fun setLoaded() {
        isLoading = false
    }


    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        totalItemCount = recyclerView.layoutManager?.itemCount ?: 0

        lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            onLoadMoreListener.onLoadMore()
            isLoading = true
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}