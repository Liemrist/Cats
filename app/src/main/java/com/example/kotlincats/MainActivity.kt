package com.example.kotlincats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincats.api.CatResponse

class MainActivity : AppCompatActivity(), UserFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: CatResponse?) {
        val fragment = UserDetailsFragment.newInstance("asd", "asd")

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_main_frame, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // TODO: learn what is val.
            val userFragment = UserFragment.newInstance(1) // TODO: pass movies here.

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_frame, userFragment) // TODO: consider using replace.
                .commit()
        }
    }



}
