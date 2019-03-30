package com.example.locationfeature

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MyActivity : AppCompatActivity() {

    class MyListener : LocationListener {

        /**
         * Called when the position of the user has changed.
         * @param location The most recent location known.
         */
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                print(location.latitude)
                print(location.longitude)
            }
        }

        /**
         * Called when the status of some provider changes. For example, when the gps provider first finds a GPS fix.
         */
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            print("Provider $provider had a status change.")
        }

        /**
         * Called when a location provider has been turned on.
         * Can be used to upgrade the used provider.
         *
         * @param provider The provider that was turned on.
         */
        override fun onProviderEnabled(provider: String?) {
            print("Provider $provider was turned on")
        }

        /**
         * Called when a location provider has been turned off.
         * Can be used to downgrade the used provider.
         *
         * @param provider The provider that was turned off.
         */
        override fun onProviderDisabled(provider: String?) {
            print("Provider $provider was turned off")
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.layout)

        val listener = MyListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates("gps", 10 * 1000, 0f, listener)
    }
}