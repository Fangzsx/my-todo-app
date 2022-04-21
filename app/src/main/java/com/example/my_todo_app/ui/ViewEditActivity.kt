package com.example.my_todo_app.ui

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.trimmedLength
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

        binding.etNote.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ViewEditActivity, R.color.gray))
            }
        }


        viewEditActivityVM.getNoteByID(id).observe(this){ note ->
            binding.etNote.setText(note.content.trim())

            //cache original note content before changing
            val currentText = binding.etNote.text.toString().trim()

            Toast.makeText(this, "$currentText", Toast.LENGTH_SHORT).show()

            binding.etNote.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    binding.btnSave.visibility = View.VISIBLE

                    if(currentText == binding.etNote.text.toString().trim()){
                        binding.btnSave.visibility = View.GONE
                    }
                }

            })

            binding.btnEdit.setOnClickListener {

                binding.etNote.apply {
                    isEnabled = true
                    requestFocus()
                    setSelection(this.text!!.trimmedLength())
                }


            }

        }



    }
}