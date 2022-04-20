package com.example.my_todo_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.my_todo_app.databinding.ActivityDashboardBinding
import com.example.my_todo_app.util.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDashboardBinding
    private lateinit var prefs : SharedPreferences


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        prefs = getSharedPreferences(ProfileSetupActivity.PACKAGE_NAME, Context.MODE_PRIVATE)
        setContentView(binding.root)

        //check if profile is already set up

        //if not yet setup, direct user to setup page
        val isProfileSetUpComplete = prefs.getBoolean(ProfileSetupActivity.IS_SETUP_COMPLETE, false)
        if(!isProfileSetUpComplete){
            Intent(this, GreetActivity::class.java).also {
                startActivity(it)
            }
        }


        //else, display user info
        val name = prefs.getString(ProfileSetupActivity.NAME, "")
        val profilePicURI = prefs.getString(ProfileSetupActivity.IMAGE_URI_STRING, "")
        binding.profilePic.setImageURI(Uri.parse(profilePicURI))

        binding.tvEncourage.text = "Let's do this, $name!"


        val formatter = SimpleDateFormat("dd MMM yyyy")
        val dateToday = Calendar.getInstance()
        val formattedDate = formatter.format(dateToday.time)
        val dayOfWeek = Calendar.DAY_OF_WEEK-4
        binding.tvDateToday.text = "Today is ${Constants.daysOfWeek[dayOfWeek]}, $formattedDate"

        binding.btnAdd.setOnClickListener {
            Intent(this, AddNoteActivity::class.java).also {
                startActivity(it)
            }
        }


    }
}