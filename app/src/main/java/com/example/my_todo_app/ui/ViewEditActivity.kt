package com.example.my_todo_app.ui

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.trimmedLength
import androidx.lifecycle.ViewModelProvider
import com.example.my_todo_app.R
import com.example.my_todo_app.databinding.ActivityViewEditBinding
import com.example.my_todo_app.db.TodoDatabase
import com.example.my_todo_app.model.Todo
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.util.KeyboardUtil
import com.example.my_todo_app.viewmodel.ViewEditActivityViewModel
import com.example.my_todo_app.viewmodel.factory.ViewEditActivityViewModelFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ViewEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewEditBinding
    private lateinit var viewEditActivityVM : ViewEditActivityViewModel
    private lateinit var viewEditActivityVMF : ViewEditActivityViewModelFactory

    override fun onBackPressed() {
        Intent(this, DashboardActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewEditBinding.inflate(layoutInflater)
        viewEditActivityVMF = ViewEditActivityViewModelFactory(NoteRepository(TodoDatabase.getInstance(this).getNoteDao()))
        viewEditActivityVM = ViewModelProvider(this, viewEditActivityVMF)[ViewEditActivityViewModel::class.java]
        setContentView(binding.root)

        //get id from dashboard activity's recyclerview item
        val id = intent.getIntExtra("noteID", 0)

        binding.etNote.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.textInputLayout.defaultHintTextColor = ColorStateList.valueOf(
                    ContextCompat.getColor(this@ViewEditActivity, R.color.gray))
            }
        }

        viewEditActivityVM.getNoteByID(id).observe(this){ note ->
            note?.let {
                updateNote(it)
            }

            binding.btnEdit.setOnClickListener {
                setEditTextToFocus()
            }

            binding.btnSave.setOnClickListener {
                val newNote = Todo(note.id, binding.etNote.text.toString().trim(),binding.cbIsDone.isChecked)
                viewEditActivityVM.addNote(newNote)
                MotionToast.createColorToast(this,"UPDATE",
                    "Todo was updated",
                    MotionToastStyle.INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))
                Intent(this, DashboardActivity::class.java).also{
                    startActivity(it)
                }
                finish()
            }

            binding.btnDelete.setOnClickListener {
                viewEditActivityVM.deleteNote(note)
                MotionToast.createColorToast(this,"REMOVE",
                    "Todo was removed from list",
                    MotionToastStyle.DELETE,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this,R.font.opensansregular))
                Intent(this, DashboardActivity::class.java).also{
                    startActivity(it)
                }
                finish()
            }
        }
    }

    private fun setEditTextToFocus() {
        binding.etNote.apply {
            isEnabled = true
            requestFocus()
            setSelection(this.text!!.trimmedLength())
        }
        KeyboardUtil.showKeyboard(this)
    }

    private fun updateNote(todo: Todo) {

        binding.cbIsDone.isChecked = todo.isDone

        binding.cbIsDone.setOnCheckedChangeListener { _, _ ->
            binding.btnSave.visibility = View.VISIBLE
        }

        binding.etNote.setText(todo.content.trim())
        //cache original note content before changing
        val currentText = binding.etNote.text.toString().trim()

        binding.etNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnSave.visibility = View.VISIBLE

                if (currentText == binding.etNote.text.toString().trim() || binding.etNote.text!!.isEmpty()) {
                    binding.btnSave.visibility = View.GONE
                }
            }
        })
    }
}