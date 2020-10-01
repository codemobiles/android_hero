package com.codemobiles.androidhero

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codemobiles.androidhero.databinding.ActivityFormBinding
import com.codemobiles.androidhero.services.APIClient
import com.codemobiles.androidhero.services.APIService
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import pl.aprilapps.easyphotopicker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class FormActivity : AppCompatActivity() {

    private var permissionGranted: Boolean = false
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
        setupEventWidget()
    }

    private fun setupEventWidget() {
        binding.camera.setOnClickListener {
            if (permissionGranted) {
                easyImage?.openCameraForImage(this@FormActivity)
            } else {
                //todo
            }
        }

        binding.gallery.setOnClickListener {
            if (permissionGranted) {
                easyImage?.openGallery(this@FormActivity)
            } else {
                //todo
            }
        }

        binding.productSubmit.setOnClickListener {
            val byteArray: ByteArray? = file?.let { file ->
                covertByteArray(file)
            }

            upload(
                binding.productEdittextName.text.toString(),
                binding.productEdittextPrice.text.toString(),
                binding.productEdittextStock.text.toString(),
                byteArray,
                file?.name
            )
        }
    }

    private fun checkRuntimePermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    if (report.areAllPermissionsGranted()) {
                        permissionGranted = true
                        Toast.makeText(applicationContext, "####", Toast.LENGTH_SHORT).show()
                    } else {
                        permissionGranted = false
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


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage!!.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(
                    imageFiles: Array<MediaFile>,
                    source: MediaSource
                ) {
                    onPhotosReturned(imageFiles)
                }

                override fun onImagePickerError(
                    error: Throwable,
                    source: MediaSource
                ) {
                    //Some error handling
                    error.printStackTrace()
                }

                override fun onCanceled(source: MediaSource) {
                    //Not necessary to remove any files manually anymore
                }
            })
    }

    private fun onPhotosReturned(returnedPhotos: Array<MediaFile>) {
        val imagesFiles: List<MediaFile> =
            ArrayList(listOf(*returnedPhotos))
        file = imagesFiles[0].file

        Glide.with(this).load(file).into(binding.productImageview)
        binding.productImageview.visibility = View.VISIBLE

        binding.photoLayout.gravity = Gravity.END
        binding.photoLayout.setPadding(0, 12, 12, 0)
    }

    private fun covertByteArray(file: File): ByteArray {
        val size = file.length().toInt()
        val bytes = ByteArray(size)
        try {
            val buf =
                BufferedInputStream(FileInputStream(file))
            buf.read(bytes, 0, bytes.size)
            buf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bytes
    }

    private fun upload(
        name: String,
        price: String,
        stock: String,
        byteArray: ByteArray?,
        fileName: String?
    ) {
        // Sent Message
        val bodyText = HashMap<String, RequestBody>().apply {
            val mediaType = MediaType.parse(MEDIA_TYPE_TEXT)
            this[API_PRODUCT_FORM_NAME] =
                RequestBody.create(mediaType, if (name.isEmpty()) "-" else name)
            this[API_PRODUCT_FORM_PRICE] =
                RequestBody.create(mediaType, if (price.isEmpty()) "0" else price)
            this[API_PRODUCT_FORM_STOCK] =
                RequestBody.create(mediaType, if (stock.isEmpty()) "0" else stock)
        }

        // Send Image
        val bodyImage: MultipartBody.Part? = byteArray?.let {
            val mediaType = MediaType.parse(MEDIA_TYPE_IMAGE)
            val reqFile = RequestBody.create(mediaType, byteArray)
            MultipartBody.Part.createFormData(API_PRODUCT_FORM_PHOTO, fileName, reqFile)
        }

        APIClient.getClient().create(APIService::class.java).addProduct(bodyText, bodyImage)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            try {
                                finish()
                            } catch (e: IOException) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "network failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })


    }


}