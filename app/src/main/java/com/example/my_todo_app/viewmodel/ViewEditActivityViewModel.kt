package com.example.my_todo_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_todo_app.model.Todo
import com.example.my_todo_app.repo.NoteRepository
import kotlinx.coroutines.launch

class ViewEditActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    fun getNoteByID(id : Int) : LiveData<Todo> = repository.getNoteByID(id)

    fun addNote(todo : Todo) = viewModelScope.launch {
        repository.addNote(todo)
    }

    fun deleteNote(todo : Todo) = viewModelScope.launch {
        repository.deleteNote(todo)
    }




}