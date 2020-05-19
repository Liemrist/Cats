package com.example.kotlincats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.kotlincats.R
import com.example.kotlincats.databinding.FragmentCatDetailsBinding
import com.example.kotlincats.domain.model.Cat

class CatDetailsFragment : Fragment() {


    private var _binding: FragmentCatDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)

        val cat: Cat = arguments?.getParcelable(ARG_CAT) ?: return

        binding.nameText.text = cat.name
        binding.infoText.text = cat.info
        binding.imageView.load(cat.photoUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
        }
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
