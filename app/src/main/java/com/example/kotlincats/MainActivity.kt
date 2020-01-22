package com.example.kotlincats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincats.list.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.activity_main_frame,
                    UserListFragment()
                )
                .commit()
        }
    }
}
