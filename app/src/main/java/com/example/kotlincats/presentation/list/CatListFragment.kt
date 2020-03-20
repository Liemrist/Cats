package com.example.kotlincats.presentation.list

import android.content.Context
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
import com.example.kotlincats.application.CatsApplication
import com.example.kotlincats.di.ViewModelFactory
import com.example.kotlincats.presentation.CatDetailsFragment
import javax.inject.Inject


/**
 * A fragment representing a list of Cats.
 */
class CatListFragment : Fragment() {


    private lateinit var listAdapter: CatListAdapter
    private lateinit var list: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: CatListViewModel

    @Inject lateinit var viewModelFactory: ViewModelFactory


    override fun onAttach(context: Context) {
        (context.applicationContext as CatsApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cat_list, container, false)

        viewModel = ViewModelProviders.of(
            this, viewModelFactory)[CatListViewModel::class.java]

        initUserList(view)

        progressBar = view.findViewById(R.id.progressBar)

        viewModel.isProgressBarVisible.observe(this, Observer { isVisible ->
            progressBar.isVisible = isVisible
            list.isVisible = !isVisible
        })

        viewModel.loadCats()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }


    private fun initUserList(view: View) {
        listAdapter = CatListAdapter { itemPosition: Int ->
            showDetailsFragment(itemPosition)
        }

        list = view.findViewById(R.id.list)
        list.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Add swipe handling to RecyclerView.
        val itemTouchHelper = context?.let {
            // FIXME: remove context from args.
            ItemTouchHelper(object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.delete(listAdapter.getCat(viewHolder.adapterPosition))

                    listAdapter.removeRow(viewHolder.adapterPosition)
                }
            })
        }
        itemTouchHelper?.attachToRecyclerView(list)

        viewModel.cats.observe(this, Observer { users ->
            listAdapter.setCats(users)
        })
    }

    private fun showDetailsFragment(userPosition: Int) {
        val fragment = CatDetailsFragment.newInstance(
            listAdapter.getCat(userPosition)
        )

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.activity_main_frame, fragment)
            ?.commit()
    }
}
