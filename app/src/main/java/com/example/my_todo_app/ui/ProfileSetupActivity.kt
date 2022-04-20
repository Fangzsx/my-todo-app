package com.example.my_todo_app.ui

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityProfileSetupBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class ProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileSetupBinding


    private var mCameraUri: Uri? = null
    private var mGalleryUri: Uri? = null
    private var mProfileUri: Uri? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri
                binding.civProfilePic.setImageURI(fileUri)
                binding.btnUpload.alpha = 0.25f
                Toast.makeText(this, mProfileUri.toString(), Toast.LENGTH_SHORT).show()

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //upload


        binding.btnUpload.setOnClickListener {
            ImagePicker.with(this)
                // Crop Image(User can choose Aspect Ratio)
                .crop()
                // User can only select image from Gallery
                .galleryOnly()
                .galleryMimeTypes( // no gif images at all
                    mimeTypes = arrayOf(
                        "image/png",
                        "image/jpg",
                        "image/jpeg"
                    )
                )
                // Image resolution will be less than 1080 x 1920
                .maxResultSize(1080, 1920)
                // .saveDir(getExternalFilesDir(null)!!)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }

        }




        binding.btnComplete.setOnClickListener {
            Intent(this, DashboardActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.etName.setOnFocusChangeListener { _, hasFocus ->

            if (hasFocus) {
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ProfileSetupActivity, R.color.pink)
                )
            }
        }
    }


}