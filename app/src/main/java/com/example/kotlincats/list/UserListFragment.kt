package com.example.kotlincats.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.R
import com.example.kotlincats.UserDetailsFragment
import com.example.kotlincats.api.CatDto
import com.example.kotlincats.model.database.User
import com.example.kotlincats.model.database.UserViewModel
import com.example.kotlincats.service.CatPhotos
import com.example.kotlincats.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A fragment representing a list of Users.
 */
class UserListFragment : Fragment() {


    private lateinit var adapter: UserListAdapter
    private lateinit var list: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        initUserList(view)

        progressBar = view.findViewById(R.id.progressBar)

        viewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

        viewModel.users.observe(this, Observer { users ->
            adapter.setCats(users)
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

//        loadPhotos() // TODO: move it outta here.
    }


    private fun initUserList(view: View) {
        adapter = UserListAdapter(emptyList()) { user: User ->
            showDetailsFragment(user)
        }

        list = view.findViewById(R.id.list)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)

        // Add swipe handling to RecyclerView.
        val itemTouchHelper = context?.let {
            ItemTouchHelper(object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    adapter.removeRow(viewHolder.adapterPosition)
                }
            })
        }
        itemTouchHelper?.attachToRecyclerView(list)
    }


    private fun loadPhotos() {
        list.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        CatPhotos.getCats(5).enqueue(object : Callback<List<CatDto>> {
            override fun onFailure(call: Call<List<CatDto>>?, t: Throwable?) {
                Snackbar.make(list, getString(R.string.api_error), Snackbar.LENGTH_LONG)
            }

            override fun onResponse(
                call: Call<List<CatDto>>?,
                response: Response<List<CatDto>>?
            ) {
                response?.let { photoResponse ->
                    if (photoResponse.isSuccessful) {
                        val body = photoResponse.body()
                        body?.let {
                            val users = mapCatsToUsers(body)
                            viewModel.insert(users)
                        }
                        list.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }


    private fun mapCatsToUsers(cats: List<CatDto>): List<User> {
        val users: ArrayList<User> = arrayListOf()

        for (cat in cats) {
            users.add(
                User(cat.hashCode(), "",cat.imageUrl)
            )
        }

        return users
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
