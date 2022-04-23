package com.example.my_todo_app.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityProfileSetupBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileSetupBinding
    private lateinit var sharedPref : SharedPreferences


    private var mProfileUri: Uri? = null
    companion object{
        const val PACKAGE_NAME = "com.example.my_todo_app_preferences"
        const val NAME : String = "nameKey"
        const val IMAGE_URI_STRING : String = "uriKey"
        const val IS_SETUP_COMPLETE : String ="isCompleteKey"
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    mProfileUri = fileUri
                    binding.civProfilePic.setImageURI(fileUri)
                    binding.btnUpload.alpha = 0.25f

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                    MotionToast.createColorToast(this,"CANCEL",
                        "Task Cancelled.",
                        MotionToastStyle.WARNING,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(this,R.font.opensansregular))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        sharedPref = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE)
        setContentView(binding.root)


        binding.btnUpload.setOnClickListener {
            uploadImage()
        }

        binding.btnComplete.setOnClickListener {

            val editor : SharedPreferences.Editor = sharedPref.edit()
            val name = binding.etName.text.toString().trim()
            val imageURI = mProfileUri.toString()

            if(name.isNotEmpty() && binding.civProfilePic.drawable != null){
                editor.putString(NAME, name)
                editor.putString(IMAGE_URI_STRING, imageURI)
                editor.putBoolean(IS_SETUP_COMPLETE, true)
                editor.apply()

                Intent(this, DashboardActivity::class.java).also {
                    startActivity(it)
                }
            }else{
                MotionToast.createColorToast(this,"ERROR",
                    "Specify your name and set image",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))
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

    private fun uploadImage() {
        ImagePicker.with(this)
            // Crop Image(User can choose Aspect Ratio)
            .crop(1f, 1f)
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


}