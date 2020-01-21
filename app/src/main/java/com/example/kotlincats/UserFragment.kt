package com.example.kotlincats

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.api.CatResponse
import com.example.kotlincats.service.CatPhotos
import com.example.kotlincats.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 */
class UserFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.list)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = UserRecyclerViewAdapter(emptyList()) {
                    cat: CatResponse -> onListFragmentInteraction(cat)
            }
        }

        val itemTouchHelper = context?.let {
            ItemTouchHelper(object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    (recyclerView.adapter as UserRecyclerViewAdapter).removeRow(viewHolder.adapterPosition)
                }
            })
        }
        itemTouchHelper?.attachToRecyclerView(recyclerView);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        loadPhotos() // TODO: move it outta here.
    }

    private fun loadPhotos() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        CatPhotos.getCats(30).enqueue(object : Callback<List<CatResponse>> {
            override fun onFailure(call: Call<List<CatResponse>>?, t: Throwable?) {
                Snackbar.make(recyclerView, getString(R.string.api_error), Snackbar.LENGTH_LONG)
            }

            override fun onResponse(
                call: Call<List<CatResponse>>?,
                response: Response<List<CatResponse>>?
            ) {
                response?.let { photoResponse ->
                    if (photoResponse.isSuccessful) {
                        val body = photoResponse.body()
                        body?.let {
                            (recyclerView.adapter as UserRecyclerViewAdapter).setCats(body)
                        }
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun onListFragmentInteraction(item: CatResponse?) {
        val fragment = UserDetailsFragment.newInstance("asd", "asd")

        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_main_frame, fragment)
            .commit()
    }
}
