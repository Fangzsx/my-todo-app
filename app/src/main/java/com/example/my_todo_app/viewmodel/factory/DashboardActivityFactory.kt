package com.example.my_todo_app.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.my_todo_app.repo.NoteRepository
import com.example.my_todo_app.viewmodel.DashboardActivityViewModel

class DashboardActivityFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardActivityViewModel(repository) as T
    }

}