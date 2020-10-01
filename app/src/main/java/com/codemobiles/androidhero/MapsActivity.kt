package com.codemobiles.androidhero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityMapsBinding
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

            // Add a marker in Sydney and move the camera
            val dummy1 = LatLng(13.7438306, 100.5151677)
            val dummy2 = LatLng(13.743330, 100.523193)
            val dummy3 = LatLng(13.743101, 100.526712)

            addMarker(dummy1)
            addMarker(dummy2)
            addMarker(dummy3)
        }
    }

    private fun addMarker(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng).title("Marker in Sydney"))
    }

}