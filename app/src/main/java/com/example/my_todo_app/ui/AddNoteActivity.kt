package com.example.my_todo_app.ui

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityAddNoteBinding
import com.example.my_todo_app.db.NoteDatabase
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.viewmodel.AddNoteActivityViewModel
import com.example.my_todo_app.viewmodel.factory.AddNoteActivityViewModelFactory

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddNoteBinding
    private lateinit var addNoteVM : AddNoteActivityViewModel
    private lateinit var addNoteVMF : AddNoteActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNoteVMF = AddNoteActivityViewModelFactory(NoteRepository(NoteDatabase.getInstance(this).getNoteDao()))
        addNoteVM = ViewModelProvider(this, addNoteVMF)[AddNoteActivityViewModel::class.java]


        binding.btnAdd.setOnClickListener {
            val content = binding.etNote.text.toString().trim()

            if(content.isNotEmpty()){
                val note = Note(0, content)
                addNoteVM.addNote(note)
                Toast.makeText(this, "Note added.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Note cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }


        binding.etNote.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.textInputLayout.hint = "I am planning to ..."
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@AddNoteActivity, R.color.gray)
                )
            }
        }
    }
}