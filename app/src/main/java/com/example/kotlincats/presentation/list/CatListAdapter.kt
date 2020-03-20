package com.example.kotlincats.presentation.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.R
import com.example.kotlincats.domain.model.Cat
import kotlinx.android.synthetic.main.fragment_cat.view.*


class CatListAdapter(private val clickListener: (itemPosition: Int) -> Unit)
    : RecyclerView.Adapter<CatListAdapter.ViewHolder>() {


    private var cats: List<Cat> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cat, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cats[position]) {
            clickListener(position)
        }
    }


    override fun getItemCount(): Int = cats.size


    fun setCats(cats: List<Cat>) {
        this.cats = cats
        notifyDataSetChanged()
    }


    fun removeRow(position : Int) {
        val cat = getCat(position)
        cats = cats.minus(cat)
        notifyItemRemoved(position)
    }


    fun getCat(position: Int): Cat = cats[position]


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cat: Cat, onClick: () -> Unit ) {
            with(itemView) {
                // FIXME: rename this view.
                item_number.text = cat.id.toString()
                content.text = cat.name
                profileImage.load(cat.photoUrl)  {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(CircleCropTransformation())
                }

                setOnClickListener { onClick() }
            }
        }
    }
}
