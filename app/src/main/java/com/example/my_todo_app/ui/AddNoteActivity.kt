package com.example.my_todo_app.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityAddNoteBinding
import com.example.my_todo_app.db.NoteDatabase
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.viewmodel.AddNoteActivityViewModel
import com.example.my_todo_app.viewmodel.factory.AddNoteActivityViewModelFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddNoteBinding
    private lateinit var addNoteVM : AddNoteActivityViewModel
    private lateinit var addNoteVMF : AddNoteActivityViewModelFactory

    override fun onBackPressed() {
        Intent(this, DashboardActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNoteVMF = AddNoteActivityViewModelFactory(NoteRepository(NoteDatabase.getInstance(this).getNoteDao()))
        addNoteVM = ViewModelProvider(this, addNoteVMF)[AddNoteActivityViewModel::class.java]


        binding.btnAdd.setOnClickListener {
            val content = binding.etNote.text.toString().trim()

            if(content.isNotEmpty()){
                val note = Note(0, content, false)
                addNoteVM.addNote(note)

                MotionToast.createColorToast(this,"SUCCESS",
                    "Note added",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))

                Intent(this, DashboardActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
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