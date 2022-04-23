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
import com.example.my_todo_app.db.TodoDatabase
import com.example.my_todo_app.model.Todo
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.viewmodel.AddNoteActivityViewModel
import com.example.my_todo_app.viewmodel.factory.AddNoteActivityViewModelFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class AddTodoActivity : AppCompatActivity() {
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

        addNoteVMF = AddNoteActivityViewModelFactory(NoteRepository(TodoDatabase.getInstance(this).getNoteDao()))
        addNoteVM = ViewModelProvider(this, addNoteVMF)[AddNoteActivityViewModel::class.java]


        binding.btnAdd.setOnClickListener {
            val content = binding.etNote.text.toString().trim()

            if(content.isNotEmpty()){
                val note = Todo(0, content, false)
                addNoteVM.addNote(note)

                MotionToast.createColorToast(this,"SUCCESS",
                    "Todo was added on list",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))

                Intent(this, DashboardActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }else{
                MotionToast.createColorToast(this,"ERROR",
                    "Text cannot be empty",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))
            }
        }

        binding.etNote.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.textInputLayout.hint = "I am planning to ..."
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@AddTodoActivity, R.color.gray)
                )
            }
        }
    }
}