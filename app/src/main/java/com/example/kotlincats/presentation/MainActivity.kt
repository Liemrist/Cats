package com.example.kotlincats.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincats.R
import com.example.kotlincats.presentation.list.CatListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_frame, CatListFragment())
                .commitNow()
        }
    }
}
