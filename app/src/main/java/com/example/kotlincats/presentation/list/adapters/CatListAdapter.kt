package com.example.kotlincats.presentation.list.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.R
import com.example.kotlincats.domain.model.Cat


class CatListAdapter(private val clickListener: (itemPosition: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private object Constant {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }


    private var cats: List<Cat?> = emptyList()


    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_cat, parent, false)
            CatViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(
                view
            )
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (cats[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }


    fun addLoadingView() {
        cats = cats.plusElement(null)
        notifyItemInserted(cats.size - 1)
    }


    fun removeLoadingView() {
        if (cats.isNotEmpty()) {
            cats = cats.minus(cats[cats.size - 1])
            notifyItemRemoved(cats.size)
        }
    }


    override fun getItemCount(): Int = cats.size


    fun setCats(cats: List<Cat>) {
        this.cats = cats
        notifyDataSetChanged()
    }


    fun removeRow(position: Int) {
        val cat = getCat(position)
        cats = cats.minus(cat)
        notifyItemRemoved(position)
    }


    fun getCat(position: Int): Cat? = cats[position]


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cat = cats[position]
        if (cat != null) {
            (holder as CatViewHolder).bind(cat) {
                clickListener(position)
            }
        }
    }
}
