package com.example.kotlincats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincats.api.CatResponse
import com.example.kotlincats.service.CatPhotos
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [UserFragment.OnListFragmentInteractionListener] interface.
 */
class UserFragment : Fragment(), OnListFragmentInteractionListener {

    // TODO: Customize parameters
    private var columnCount = 1
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.list)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = UserRecyclerViewAdapter(emptyList(), this@UserFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPhotos() // TODO: move outta here.
    }

    private fun loadPhotos() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        Log.d("TAG", "CHECK 1")

        CatPhotos.getCats().enqueue(object : Callback<List<CatResponse>> {
            override fun onFailure(call: Call<List<CatResponse>>?, t: Throwable?) {
                Snackbar.make(recyclerView, "API ERROR", Snackbar.LENGTH_LONG)
                Log.e("TAG", "Problems getting Photos with error: $t.msg")
            }

            override fun onResponse(call: Call<List<CatResponse>>?, response: Response<List<CatResponse>>?) {
                response?.let { photoResponse ->
                    if (photoResponse.isSuccessful) {
                        val body = photoResponse.body()
                        body?.let {
                            Log.d("TAG2", "Received ${body.size} photos $body")
                            (recyclerView.adapter as UserRecyclerViewAdapter).setCats(body)
                        }
//                        recycler_view.scrollToPosition(0)
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
        Log.d("TAG", "CHECK 2")

    }

    override fun onListFragmentInteraction(item: CatResponse?) {
        val fragment = UserDetailsFragment.newInstance("asd", "asd")

        // TODO: LEARN HOW TO CHANGE FRAGMENTS FROM ANOTHER FRAGMENTS.
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_main_frame, fragment)
            .commit()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson
 * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
 * for more information.
 */
interface OnListFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onListFragmentInteraction(item: CatResponse?)
}