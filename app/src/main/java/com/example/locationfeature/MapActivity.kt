package com.example.locationfeature

import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdate


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (intent.extras != null) {
            val sent = intent.extras["position"]
            if (sent != null) {
                val location = sent as Location
                val newPosition = LatLng(location.latitude, location.longitude)
                mMap.addMarker(MarkerOptions().position(newPosition).title("You are here"))

                val center = CameraUpdateFactory.newLatLng(newPosition)
                val zoom = CameraUpdateFactory.zoomTo(15f)
                mMap.moveCamera(center)
                mMap.animateCamera(zoom)
            }
        }
    }
}