package com.codemobiles.androidhero

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.androidhero.databinding.ActivityFormBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private var easyImage: EasyImage? = null
    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        easyImage = EasyImage.Builder(this)
            .setChooserTitle("Pick media")
            .setCopyImagesToPublicGalleryFolder(false)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setFolderName("EasyImage sample")
            .allowMultiple(true)
            .build()

        checkRuntimePermission()
    }

    private fun checkRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    if (report.areAllPermissionsGranted()) {
                        easyImage?.openCameraForImage(this@FormActivity)
                        Toast.makeText(applicationContext, "####", Toast.LENGTH_SHORT).show()
                    } else {
                        //todo
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
}