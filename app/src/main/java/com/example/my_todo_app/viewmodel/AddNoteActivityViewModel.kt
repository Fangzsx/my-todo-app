package com.example.my_todo_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.repo.NoteRepository
import kotlinx.coroutines.launch

class AddNoteActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    fun addNote(note : Note) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun deleteNote(note : Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }
}