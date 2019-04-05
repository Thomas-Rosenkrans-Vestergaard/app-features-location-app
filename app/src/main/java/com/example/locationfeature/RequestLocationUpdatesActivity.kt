package com.example.locationfeature

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.LocationListener
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_request_location_updates.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RequestLocationUpdatesActivity : AppCompatActivity(), LocationListener, AnkoLogger, OnItemSelectedListener {

    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private var numberOfUpdates = 0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_location_updates)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 1000, 0f, this)
        this.currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        ArrayAdapter.createFromResource(this, R.array.providerChoices, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = this

        showOnMapButton.onClick {
            if (currentLocation == null) {
                toast("No location information to show.")
            } else {
                val intent = Intent(this@RequestLocationUpdatesActivity, MapActivity::class.java)
                intent.putExtra("position", currentLocation)
                startActivity(intent)
            }
        }

        reverseGeocode.onClick {
            val location = currentLocation
            if (location == null) {
                toast("Cannot perform reverse geocoding without position information.")
            } else {
                val geoCoder = Geocoder(this@RequestLocationUpdatesActivity)
                val addresses = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses != null && addresses.size > 0) {
                    val address = addresses[0]
                    val sb = StringBuilder()
                    for (i in 0 until address.maxAddressLineIndex) {
                        sb.append(address.getAddressLine(i)).append(", ")
                    }
                    sb.append(address.locality).append(", ")
                    sb.append(address.postalCode).append(", ")
                    sb.append(address.countryName)
                    addressText.text = sb.toString()
                }
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            toast("We were just notified of a location change.")
            currentLocation = location
            numberOfUpdates++
            updateDisplay(location)
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    private fun updateDisplay(location: Location?) {
        if (location != null) {
            longitudeValue.text = location.longitude.toString()
            latitudeValue.text = location.latitude.toString()
            altitudeValue.text = location.altitude.toString()
            accuracyValue.text = location.accuracy.toString()
            updatesValue.text = numberOfUpdates.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        switchProviders(
            when (position) {
                0 -> "gps"
                1 -> "network"
                else -> "passive"
            }
        )
    }

    @SuppressLint("MissingPermission")
    private fun switchProviders(provider: String) {
        this.currentLocation = null
        longitudeValue.text = ""
        latitudeValue.text = ""
        altitudeValue.text = ""
        accuracyValue.text = ""
        numberOfUpdates = 0
        updatesValue.text = numberOfUpdates.toString()
        locationManager.removeUpdates(this)
        locationManager.requestLocationUpdates(provider, 5 * 1000, 0f, this)
        toast("Switched to provider $provider")
    }

    /**
     * Remove the active listener.
     */
    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(this)
    }
}
