package com.example.kotlincats.presentation.catList.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.R
import com.example.kotlincats.domain.models.Cat
import kotlinx.android.synthetic.main.cat_list_item.view.*

class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cat: Cat, listener: CatListAdapter.Listener ) {
        with(itemView) {
            item_number.text = cat.id.toString()
            content.text = cat.name
            profileImage.load(cat.photoUrl)  {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }

            setOnClickListener { listener.onClick(cat) }
        }
    }
}