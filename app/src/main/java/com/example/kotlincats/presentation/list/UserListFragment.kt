package com.example.kotlincats.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.R
import com.example.kotlincats.presentation.UserDetailsFragment
import com.example.kotlincats.model.User
import com.example.kotlincats.utils.SwipeToDeleteCallback


/**
 * A fragment representing a list of Users.
 */
class UserListFragment : Fragment() {


    private lateinit var listAdapter: UserListAdapter
    private lateinit var list: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        viewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

        initUserList(view)

        progressBar = view.findViewById(R.id.progressBar)

        viewModel.isProgressBarVisible.observe(this, Observer { isVisible ->
            progressBar.isVisible = isVisible
            list.isVisible = !isVisible
        })

        viewModel.loadUsers()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }


    private fun initUserList(view: View) {
        listAdapter = UserListAdapter(emptyList()) { user: User ->
            showDetailsFragment(user)
        }

        list = view.findViewById(R.id.list)
        list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Add swipe handling to RecyclerView.
        val itemTouchHelper = context?.let {
            ItemTouchHelper(object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val user = listAdapter.getUser(viewHolder.adapterPosition)
                    viewModel.delete(user)

                    listAdapter.removeRow(viewHolder.adapterPosition)
                }
            })
        }
        itemTouchHelper?.attachToRecyclerView(list)

        viewModel.users.observe(this, Observer { users ->
            listAdapter.setUsers(users)
        })
    }

    private fun showDetailsFragment(user: User) {
        val fragment = UserDetailsFragment.newInstance(user)

        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_main_frame, fragment)
            .commit()
    }
}
