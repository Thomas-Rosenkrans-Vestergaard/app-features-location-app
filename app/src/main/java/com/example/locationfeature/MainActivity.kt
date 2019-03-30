package com.example.locationfeature

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addClickHandlers()
    }

    private fun addClickHandlers() {

        lastKnownLocationButton.onClick {
            val intent = Intent(this@MainActivity, LastKnownLocationActivity::class.java)
            startActivity(intent)
        }

        listenButton.onClick {
            val intent = Intent(this@MainActivity, RequestLocationUpdatesActivity::class.java)
            startActivity(intent)
        }

        showMapButton.onClick {
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
        }
    }
}
