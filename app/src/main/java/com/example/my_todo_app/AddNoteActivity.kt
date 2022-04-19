package com.example.my_todo_app

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.my_todo_app.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etNote.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(p0: View?, hasFocus: Boolean) {
                if(hasFocus){
                    binding.textInputLayout.hint = "I am planning to ..."
                    binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                        ContextCompat.getColor(this@AddNoteActivity, R.color.gray))
                }
            }

        })
    }
}