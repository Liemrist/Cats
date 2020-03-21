package com.example.kotlincats.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.kotlincats.di.viewModel.ViewModelFactory
import com.example.kotlincats.presentation.CatDetailsFragment
import kotlinx.android.synthetic.main.fragment_cat_list.*
import javax.inject.Inject


/**
 * A fragment representing a list of Cats.
 */
class CatListFragment : Fragment() {


    private lateinit var catListAdapter: CatListAdapter
    private lateinit var viewModel: CatListViewModel

    @Inject lateinit var applicationContext: Context
    @Inject lateinit var viewModelFactory: ViewModelFactory


    override fun onAttach(context: Context) {
        (context.applicationContext as CatsApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cat_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCatList()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CatListViewModel::class.java]

        viewModel.isProgressBarVisible.observe(this, Observer { isVisible ->
            progressBar.isVisible = isVisible
            catList.isVisible = !isVisible
        })

        viewModel.cats.observe(this, Observer { cats ->
            catListAdapter.setCats(cats)
        })

        // Action bar setup.
        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.loadCats()
    }


    private fun initCatList() {
        catListAdapter = CatListAdapter { itemPosition: Int ->
            showDetailsFragment(itemPosition)
        }

        catList.apply {
            adapter = catListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Add swipe handling to RecyclerView.
        ItemTouchHelper(object : SwipeToDeleteCallback(applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeListRow(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(catList)
    }


    private fun removeListRow(position: Int) {
        viewModel.delete(catListAdapter.getCat(position))
        catListAdapter.removeRow(position)
    }


    private fun showDetailsFragment(position: Int) {
        val fragment = CatDetailsFragment.newInstance(
            catListAdapter.getCat(position)
        )

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.activity_main_frame, fragment)
            ?.commit()
    }
}
