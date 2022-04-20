package com.example.my_todo_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.repo.NoteRepository

class DashboardActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    fun getNotes() : LiveData<List<Note>> = repository.notes

}