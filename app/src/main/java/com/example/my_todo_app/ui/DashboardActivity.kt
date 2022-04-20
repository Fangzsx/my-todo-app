package com.example.my_todo_app.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.my_todo_app.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDashboardBinding
    private lateinit var prefs : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        prefs = getSharedPreferences(ProfileSetupActivity.PACKAGE_NAME, Context.MODE_PRIVATE)

        //check if profile is already set up
        val isProfileSetUpComplete = prefs.getBoolean(ProfileSetupActivity.IS_SETUP_COMPLETE, false)
        if(!isProfileSetUpComplete){
            Intent(this, GreetActivity::class.java).also {
                startActivity(it)
            }
        }


        val name = prefs.getString(ProfileSetupActivity.NAME, "")
        val profilePicURI = prefs.getString(ProfileSetupActivity.IMAGE_URI_STRING, "")

        binding.profilePic.setImageURI(Uri.parse(profilePicURI))

        setContentView(binding.root)
        val imageURI = prefs.getString(ProfileSetupActivity.IMAGE_URI_STRING, "")
        Toast.makeText(this, imageURI, Toast.LENGTH_SHORT).show()



        binding.btnAdd.setOnClickListener {
            Intent(this, GreetActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}