package com.nazirjon.testtaskapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nazirjon.testtaskapp.R
import com.nazirjon.testtaskapp.ui.auth.AuthFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(
                            R.id.fragment_container,
                            AuthFragment.newInstance(false),
                            "tag"
                    )
                    .commit()
        }
    }
}