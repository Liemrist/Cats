package com.example.kotlincats


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.api.CatResponse
import kotlinx.android.synthetic.main.fragment_user.view.*


class UserRecyclerViewAdapter(
    private val users: List<CatResponse>,
    private val interactionListener: (cat: CatResponse) -> Unit
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private var cats: List<CatResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cats[position]) {
                cat: CatResponse -> interactionListener(cat)
        }
    }

    override fun getItemCount(): Int = cats.size

    fun setCats(cats: List<CatResponse>) {
        this.cats = cats
//        notifyDataSetChanged()
    }

    fun removeRow(row : Int) {
        val cat = cats[row]
        cats = cats.minus(cat)
        notifyItemRemoved(row)
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val idView: TextView = itemView.item_number
        private val contentView: TextView = itemView.content
        private val profileImage: ImageView = itemView.profileImage

        // TODO: rename "item" to something more meaningful.
        fun bind(item: CatResponse, clickListener: (CatResponse) -> Unit ) {
            profileImage.load(item.imageUrl)  {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
            idView.text = item.id
            contentView.text = item.imageUrl
            itemView.setOnClickListener { clickListener(item) }
        }
    }
}
