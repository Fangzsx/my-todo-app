package com.example.my_todo_app

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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

        val currentText = binding.etNote.text.toString()

        binding.etNote.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.visibility = View.VISIBLE

                if(currentText == binding.etNote.text.toString()){
                    binding.btnSave.visibility = View.GONE
                }
            }

        })

    }
}