package com.example.my_todo_app.ui

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityViewEditBinding
import com.example.my_todo_app.db.NoteDatabase
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.viewmodel.ViewEditActivityViewModel
import com.example.my_todo_app.viewmodel.factory.ViewEditActivityViewModelFactory

class ViewEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewEditBinding
    private lateinit var viewEditActivityVM : ViewEditActivityViewModel
    private lateinit var viewEditActivityVMF : ViewEditActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewEditBinding.inflate(layoutInflater)
        viewEditActivityVMF = ViewEditActivityViewModelFactory(NoteRepository(NoteDatabase.getInstance(this).getNoteDao()))
        viewEditActivityVM = ViewModelProvider(this, viewEditActivityVMF).get(ViewEditActivityViewModel::class.java)
        setContentView(binding.root)

        val id = intent.getIntExtra("noteID", 0)

        binding.etNote.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ViewEditActivity, R.color.gray))
            }
        }

        viewEditActivityVM.getNoteByID(id).observe(this){
            Toast.makeText(this, it.content.toString(), Toast.LENGTH_SHORT).show()
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