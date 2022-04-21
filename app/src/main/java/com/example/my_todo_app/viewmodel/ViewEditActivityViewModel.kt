package com.example.my_todo_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_todo_app.model.Note
import com.example.my_todo_app.repo.NoteRepository
import kotlinx.coroutines.launch

class ViewEditActivityViewModel(
    private val repository: NoteRepository
) : ViewModel() {







}