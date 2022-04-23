package com.example.my_todo_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_todo_app.model.Todo
import com.example.my_todo_app.repo.NoteRepository
import kotlinx.coroutines.launch

class AddNoteActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    fun addNote(todo : Todo) = viewModelScope.launch {
        repository.addNote(todo)
    }

}