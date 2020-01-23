package com.example.kotlincats.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.R
import com.example.kotlincats.model.User
import kotlinx.android.synthetic.main.fragment_user.view.*


class UserListAdapter(
    private var users: List<User>,
    private val clickListener: (user: User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position]) { user: User ->
            clickListener(user)
        }
    }


    override fun getItemCount(): Int = users.size


    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }


    fun removeRow(position : Int) {
        val user = getUser(position)
        users = users.minus(user)
        notifyItemRemoved(position)
    }


    fun getUser(position: Int): User {
        return users[position]
    }


    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {


        private val idView: TextView = itemView.item_number
        private val contentView: TextView = itemView.content
        private val profileImage: ImageView = itemView.profileImage


        fun bind(user: User, clickListener: (User) -> Unit ) {
            profileImage.load(user.photoUrl)  {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
            }
            idView.text = user.id.toString()
            contentView.text = user.name

            itemView.setOnClickListener { clickListener(user) }
        }
    }
}
