package com.example.kotlincats.presentation.list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.R
import com.example.kotlincats.domain.model.Cat

class CatListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var cats: List<Cat?> = emptyList()


    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cat_list_item, parent, false)
            CatViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            cats[position]?.let { (holder as CatViewHolder).bind(it, listener) }
        }
    }


    override fun getItemCount(): Int = cats.size


    override fun getItemViewType(position: Int): Int {
        return if (cats[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }


    fun addLoadingView() {
        cats = cats.plusElement(null)
        notifyItemInserted(cats.size - 1)
    }


    fun removeLoadingView() {
        val position = cats.indexOf(null)
        if (position != -1) {
            cats.minus(cats[position])
            notifyItemRemoved(position)
        }
    }


    fun setCats(cats: List<Cat>) {
        this.cats = cats
        notifyDataSetChanged()
    }


    fun removeRow(position: Int) {
        val cat = cats[position]
        cats = cats.minus(cat)
        notifyItemRemoved(position)
    }


    fun getCat(position: Int): Cat? = cats[position]


    interface Listener {
        fun onClick(cat: Cat)
    }


    companion object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }
}
