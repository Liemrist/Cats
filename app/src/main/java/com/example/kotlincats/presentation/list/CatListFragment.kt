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
import com.example.kotlincats.databinding.FragmentCatListBinding
import com.example.kotlincats.di.viewModel.ViewModelFactory
import com.example.kotlincats.presentation.CatDetailsFragment
import com.example.kotlincats.presentation.list.adapters.CatListAdapter
import com.example.kotlincats.presentation.list.adapters.CatsScrollListener
import com.example.kotlincats.presentation.list.adapters.SwipeToDeleteCallback
import javax.inject.Inject

/**
 * A fragment representing a list of Cats.
 */
class CatListFragment : Fragment() {


    private var _binding: FragmentCatListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCatList()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CatListViewModel::class.java]

        viewModel.isProgressBarVisible.observe(viewLifecycleOwner, Observer { isVisible ->
            binding.progressBar.isVisible = isVisible
            binding.catList.isVisible = !isVisible
        })

        viewModel.cats.observe(viewLifecycleOwner, Observer { cats ->
            catListAdapter.setCats(cats)
        })

        viewModel.handleLoadMore.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                catListAdapter.removeLoadingView()
                catsScrollListener.setLoaded()
            }
        })

        // Action bar setup.
        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.loadCats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCatList() {
        catListAdapter =
            CatListAdapter { itemPosition: Int ->
                showDetailsFragment(itemPosition)
            }

        binding.catList.apply {
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
        }).attachToRecyclerView(binding.catList)

        // Handle load more.
        catsScrollListener = CatsScrollListener()
        catsScrollListener.setOnLoadMoreListener(object : CatsScrollListener.OnLoadMoreListener {
            override fun onLoadMore() {
                catListAdapter.addLoadingView()
                viewModel.loadMoreCats()
            }
        })
        binding.catList.addOnScrollListener(catsScrollListener)
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
            ?.replace(R.id.fragment_container_view, fragment)
            ?.commit()
    }
}
