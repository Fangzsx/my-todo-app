package com.example.my_todo_app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_todo_app.adapter.NoteAdapter
import com.example.my_todo_app.databinding.ActivityDashboardBinding
import com.example.my_todo_app.db.NoteDatabase
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.util.Constants
import com.example.my_todo_app.viewmodel.DashboardActivityViewModel
import com.example.my_todo_app.viewmodel.factory.DashboardActivityFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class DashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDashboardBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var dashboardActivityVM : DashboardActivityViewModel
    private lateinit var dashboardActivityVMF : DashboardActivityFactory
    private lateinit var noteAdapter : NoteAdapter



    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        prefs = getSharedPreferences(ProfileSetupActivity.PACKAGE_NAME, Context.MODE_PRIVATE)
        dashboardActivityVMF = DashboardActivityFactory(NoteRepository(NoteDatabase.getInstance(this).getNoteDao()))
        dashboardActivityVM = ViewModelProvider(this, dashboardActivityVMF).get(DashboardActivityViewModel::class.java)
        noteAdapter = NoteAdapter()
        setContentView(binding.root)

        verifyUserProfileSetup() //redirect to profile setup
        //if user already setup, then proceed to regular flow
        showUserDetails()
        showRandomQuote()
        setUpTodoRecyclerView()

        //for adding notes
        binding.btnAdd.setOnClickListener {
            Intent(this, AddNoteActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun showRandomQuote() {
        dashboardActivityVM.getRandomQuote()
        dashboardActivityVM.quote.observe(this){ quote->
            binding.tvQuote.text = quote.q
        }
    }

    private fun setUpTodoRecyclerView() {
        dashboardActivityVM.getNotes().observe(this) { noteList ->
            noteAdapter.differ.submitList(noteList)
        }

        binding.rvTodos.apply {
            layoutManager =
                LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.VERTICAL, false)
            adapter = noteAdapter
        }
        noteAdapter.onItemClick = { note ->
            Intent(this, ViewEditActivity::class.java).apply {
                putExtra("noteID", note.id)
                startActivity(this)
                finish()
            }
        }
    }

    private fun showUserDetails() {
        val name = prefs.getString(ProfileSetupActivity.NAME, "")
        val profilePicURI = prefs.getString(ProfileSetupActivity.IMAGE_URI_STRING, "")
        binding.profilePic.setImageURI(Uri.parse(profilePicURI))
        binding.tvEncourage.text = "Let's do this, $name!"
        showCurrentDate()
    }

    private fun showCurrentDate() {
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val dateToday = Calendar.getInstance()
        val formattedDate = formatter.format(dateToday.time)
        val dayOfWeek = Calendar.DAY_OF_WEEK - 4
        binding.tvDateToday.text = "Today is $formattedDate"
    }

    private fun verifyUserProfileSetup() {
        //check if profile is already set up
        //if not yet setup, direct user to setup page
        val isProfileSetUpComplete = prefs.getBoolean(ProfileSetupActivity.IS_SETUP_COMPLETE, false)
        if (!isProfileSetUpComplete) {
            Intent(this, GreetActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}