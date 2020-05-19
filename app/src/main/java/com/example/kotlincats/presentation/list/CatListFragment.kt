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
import com.example.kotlincats.domain.model.Cat
import com.example.kotlincats.presentation.CatDetailsFragment
import com.example.kotlincats.presentation.list.adapters.CatListAdapter
import com.example.kotlincats.presentation.list.adapters.CatsScrollListener
import com.example.kotlincats.presentation.list.adapters.SwipeToDeleteCallback
import javax.inject.Inject

/**
 * A fragment representing a list of Cats.
 */
class CatListFragment : Fragment(), CatListAdapter.Listener, CatsScrollListener.LoadMoreListener {


    private var _binding: FragmentCatListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val catListAdapter = CatListAdapter(this)
    private val catsScrollListener =  CatsScrollListener(this)

    private lateinit var viewModel: CatListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onAttach(context: Context) {
        (context.applicationContext as CatsApplication).component.inject(this)
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

        setupActionBar()

        initCatList()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[CatListViewModel::class.java]

        viewModel.isProgressBarVisible.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { isVisible ->
                binding.progressBar.isVisible = isVisible
                binding.catList.isVisible = !isVisible
            }
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

        viewModel.loadCats()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(cat: Cat) {
        showDetailsFragment(cat)
    }


    override fun onScrolledToBottom() {
        catListAdapter.addLoadingView()
        viewModel.loadMoreCats()
    }


    private fun initCatList() {
        binding.catList.apply {
            adapter = catListAdapter
            layoutManager = LinearLayoutManager(context)

            // Handle load more.
            addOnScrollListener(catsScrollListener)
        }

        // Handle swipe.
        context?.let {
            ItemTouchHelper(object : SwipeToDeleteCallback(it) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    removeListRow(viewHolder.adapterPosition)
                }
            }).attachToRecyclerView(binding.catList)
        }
    }


    private fun setupActionBar() {
        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }


    private fun removeListRow(position: Int) {
        val cat = catListAdapter.getCat(position) ?: return
        viewModel.delete(cat)
        catListAdapter.removeRow(position)
    }


    private fun showDetailsFragment(cat: Cat) {
        val fragment = CatDetailsFragment.newInstance(cat)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.fragment_container_view, fragment)
            ?.commit()
    }
}
