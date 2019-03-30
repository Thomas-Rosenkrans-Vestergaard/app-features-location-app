package com.example.locationfeature

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.util.Log
import kotlinx.android.synthetic.main.activity_current_location.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.*

class LastKnownLocationActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var locationManager: LocationManager
    private val permissionRequestCode = 0
    private var hasPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_location)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        updateButton.onClick {
            updateLocation()
        }
    }

    /**
     * Checks whether or not we have permissions to access the location data.
     *
     * Updates the hasPermissions field. Requests permission from the user.
     */
    private fun checkPermissions() {
        if (checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), permissionRequestCode)
        } else {
            hasPermissions = true
        }
    }

    /**
     * The callback that handles responses to requests for access to location data.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            permissionRequestCode -> { // ACCESS_FINE_LOCATION
                this.hasPermissions = grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED
                if (this.hasPermissions)
                    updateLocation()
                else
                    toast("You must grant location permissions for the application to work.")
            }
        }
    }

    /**
     * Retrieves the current location of the user.
     */
    @SuppressLint("MissingPermission")
    private fun updateLocation() {

        if (!this.hasPermissions) {
            checkPermissions()
            return
        }

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location == null) {
            toast("Could not retrieve the last known location.")
        } else {
            longitudeValue.text = location.longitude.toString()
            latitudeValue.text = location.latitude.toString()
            altitudeValue.text = location.altitude.toString()
            accuracyValue.text = location.accuracy.toString()
        }
    }
}
