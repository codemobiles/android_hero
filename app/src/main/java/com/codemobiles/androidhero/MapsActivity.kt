package com.codemobiles.androidhero

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityMapsBinding
import com.codemobiles.androidhero.databinding.CustomInfoBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


private const val FASTEST_INTERVAL: Long = 1000
private const val UPDATE_INTERVAL: Long = 3000

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap

    private var mCurrentLocation: Location? = null
    private var mLocationProvider: FusedLocationProviderClient? = null
    private var mCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            setupMap()
            setupEventWidget()
            checkRuntimePermission()
            pinDatabase()
        }
    }

    private fun setupEventWidget() {
        binding.tracking.visibility = View.GONE
        binding.tracking.setOnClickListener {
            mMap.clear()
            trackLocation()
        }
    }

    private fun pinDatabase() {
        val result = arrayListOf<LatLng>(
            LatLng(13.7438306, 100.5151677),
            LatLng(13.743330, 100.523193),
            LatLng(13.743101, 100.526712),
        )

        result.forEach{ latLng -> addMarker(latLng)}
    }

    private fun setupMap() {
        val latLng = LatLng(13.7438306, 100.5151677)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))

        mMap.isTrafficEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMarkerClickListener { marker ->
            val _intent = Intent(applicationContext, StreetViewActivity::class.java)
            _intent.putExtra("lat", marker.position.latitude)
            _intent.putExtra("lng", marker.position.longitude)
            startActivity(_intent)

            marker.showInfoWindow()

            true
        }

        mMap.setOnInfoWindowClickListener { marker ->
            val _intent = Intent(applicationContext, StreetViewActivity::class.java)
            _intent.putExtra("lat", marker.position.latitude)
            _intent.putExtra("lng", marker.position.longitude)
            startActivity(_intent)
        }

        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                 return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val binding = CustomInfoBinding.inflate(layoutInflater)

                binding.infoTitle.text = "Codemobiles company"
                binding.infoSubTitle.text = "Training Center"
                binding.infoImage.setImageResource(R.drawable.logo)

                return binding.root
            }
        })



    }

    private fun addMarker(latLng: LatLng) {
        val marker = MarkerOptions()
        marker.position(latLng)
        marker.title("codemobiles")
        marker.snippet("android core")
        marker.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("pin", 170, 170)))

        mMap.addMarker(marker)
    }

    private fun resizeMapIcons(iconName: String?, width: Int, height: Int): Bitmap? {
        val imageBitmap = BitmapFactory.decodeResource(
            resources, resources.getIdentifier(
                iconName, "drawable",
                packageName
            )
        )
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

    private fun checkRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ).withListener(object : MultiplePermissionsListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    if (report.areAllPermissionsGranted()) {
                        mMap.isMyLocationEnabled = true
                        binding.tracking.visibility = View.VISIBLE
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }

    @SuppressLint("MissingPermission")
    private fun trackLocation() {
        val request = LocationRequest()
        request.interval = UPDATE_INTERVAL
        request.fastestInterval = FASTEST_INTERVAL
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mCallback = object : LocationCallback() {
            override fun onLocationResult(resut: LocationResult?) {
                super.onLocationResult(resut)
                val currentLocation = resut!!.lastLocation
                if (currentLocation != null) {
                    mCurrentLocation = currentLocation
                    Log.d("my_location", "${mCurrentLocation!!.latitude}")
                    val latLng = LatLng(mCurrentLocation!!.latitude, mCurrentLocation!!.longitude)
                    addMarker(latLng)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                }
            }
        }

        mLocationProvider!!.requestLocationUpdates(request, mCallback, Looper.myLooper())
    }

}