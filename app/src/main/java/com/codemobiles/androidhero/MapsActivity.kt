package com.codemobiles.androidhero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            setupMap()
        }
    }

    private fun setupMap() {
        val latLng = LatLng(13.7438306, 100.5151677)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))

        mMap.isTrafficEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun addMarker(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng).title("Marker in Sydney"))
    }

}