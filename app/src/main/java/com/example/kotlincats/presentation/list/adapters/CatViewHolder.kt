package com.example.kotlincats.presentation.list.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.R
import com.example.kotlincats.domain.model.Cat
import kotlinx.android.synthetic.main.fragment_cat.view.*


class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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