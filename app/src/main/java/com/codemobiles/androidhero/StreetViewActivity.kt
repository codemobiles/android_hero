package com.codemobiles.androidhero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_street_view.*

class StreetViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street_view)

        val streetViewFragment = street_view as SupportStreetViewPanoramaFragment
        streetViewFragment.getStreetViewPanoramaAsync { streetViewPanorama ->
            val lat = intent.getDoubleExtra("lat", 0.0)
            val lng = intent.getDoubleExtra("lng", 0.0)
            streetViewPanorama.setPosition(LatLng(lat, lng))
        }

    }
}