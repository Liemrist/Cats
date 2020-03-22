package com.example.kotlincats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.R
import com.example.kotlincats.databinding.FragmentCatDetailsBinding
import com.example.kotlincats.domain.model.Cat

class CatDetailsFragment : Fragment() {


    private var _binding: FragmentCatDetailsBinding? = null
    private val binding get() = _binding!!

    private var cat: Cat? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cat = arguments?.getParcelable(ARG_CAT)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameText.text = cat?.name ?: ""

        binding.infoText.text = cat?.info ?: ""

        binding.imageView.load(cat?.photoUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    companion object {

        private const val ARG_CAT = "ARG_CAT"

        fun newInstance(cat: Cat): CatDetailsFragment {
            return CatDetailsFragment().apply {
                arguments = bundleOf(ARG_CAT to cat)
            }
        }
    }
}
