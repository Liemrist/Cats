package com.example.kotlincats


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.kotlincats.model.database.User


class UserDetailsFragment : Fragment() {

    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getParcelable(ARG_USER)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)

        val textView = view.findViewById<TextView>(R.id.nameText)
        textView.text = user?.name ?: ""

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.load(user?.photoUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        val actionBar: ActionBar? = activity?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
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

        private const val ARG_USER = "ARG_USER"

        fun newInstance(user: User): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }
}
