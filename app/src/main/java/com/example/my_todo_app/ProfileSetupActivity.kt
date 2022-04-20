package com.example.my_todo_app

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.my_todo_app.databinding.ActivityProfileSetupBinding

class ProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComplete.setOnClickListener {
            Intent(this, DashboardActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.etName.setOnFocusChangeListener { _, hasFocus ->

            if(hasFocus){
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ProfileSetupActivity, R.color.pink))
            }
        }


    }
}