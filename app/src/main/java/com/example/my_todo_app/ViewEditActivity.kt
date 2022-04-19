package com.example.my_todo_app

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.my_todo_app.databinding.ActivityViewEditBinding

class ViewEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNote.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ViewEditActivity, R.color.gray))
            }
        }

    }
}