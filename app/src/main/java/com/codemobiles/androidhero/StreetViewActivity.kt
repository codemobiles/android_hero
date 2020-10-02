package com.codemobiles.androidhero

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityStreetViewBinding
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_street_view.*

class StreetViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStreetViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStreetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val streetViewFragment = street_view as SupportStreetViewPanoramaFragment
        streetViewFragment.getStreetViewPanoramaAsync { streetViewPanorama ->
            val lat = intent.getDoubleExtra("lat", 0.0)
            val lng = intent.getDoubleExtra("lng", 0.0)
            streetViewPanorama.setPosition(LatLng(lat, lng))
        }

        binding.navigator.setOnClickListener {
//            val startString = "00.000, 00.000"
//            val destString = "00.000, 00.000"
//
//            val url = "http://maps.google.com/maps?saddr=$startString&daddr=$destString"

            val lat = intent.getDoubleExtra("lat", 0.0)
            val lng = intent.getDoubleExtra("lng", 0.0)
            val url = "http://maps.google.com/maps?daddr=$lat,$lng"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        // basic setup actionbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return true
    }


}