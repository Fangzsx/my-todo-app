package com.example.my_todo_app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_todo_app.databinding.ActivityGreetBinding

class GreetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGreetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            Intent(this, ProfileSetupActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}