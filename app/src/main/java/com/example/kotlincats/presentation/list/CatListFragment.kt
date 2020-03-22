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
import com.example.kotlincats.presentation.list.adapters.CatListAdapter
import com.example.kotlincats.presentation.list.adapters.CatsScrollListener
import com.example.kotlincats.presentation.list.adapters.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_cat_list.*
import javax.inject.Inject

/**
 * A fragment representing a list of Cats.
 */
class CatListFragment : Fragment() {


    private lateinit var catListAdapter: CatListAdapter
    private lateinit var catsScrollListener: CatsScrollListener
    private lateinit var viewModel: CatListViewModel


    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


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

        viewModel.handleLoadMore.observe(this, Observer {
            catListAdapter.removeLoadingView()
            catsScrollListener.setLoaded()
        })

        // Action bar setup.
        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.loadCats()
    }


    private fun initCatList() {
        catListAdapter =
            CatListAdapter { itemPosition: Int ->
                showDetailsFragment(itemPosition)
            }

        catList.apply {
            adapter = catListAdapter
            layoutManager = LinearLayoutManager(context)
            // setHasFixedSize(true) // TODO: check.
        }

        // Handle swipe.
        ItemTouchHelper(object : SwipeToDeleteCallback(applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO: remove swipe handling for the loading item.
                removeListRow(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(catList)

        // Handle load more.
        catsScrollListener = CatsScrollListener()
        catsScrollListener.setOnLoadMoreListener(object : CatsScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                catListAdapter.addLoadingView()
                viewModel.loadMoreCats()
            }
        })
        catList.addOnScrollListener(catsScrollListener)
    }


    private fun removeListRow(position: Int) {
        val cat = catListAdapter.getCat(position) ?: return
        viewModel.delete(cat)
        catListAdapter.removeRow(position)
    }


    private fun showDetailsFragment(position: Int) {
        // TODO: show error instead of return.
        val cat = catListAdapter.getCat(position) ?: return

        val fragment = CatDetailsFragment.newInstance(cat)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.activity_main_frame, fragment)
            ?.commit()
    }
}
