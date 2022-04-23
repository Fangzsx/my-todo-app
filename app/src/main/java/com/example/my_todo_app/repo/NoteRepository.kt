package com.example.my_todo_app.repo

import androidx.lifecycle.LiveData
import com.example.my_todo_app.db.TodoDao
import com.example.my_todo_app.model.Todo

class NoteRepository(
    private val todoDao : TodoDao
) {

    val notes : LiveData<List<Todo>> = todoDao.getNotes()

    fun getNoteByID(id : Int) : LiveData<Todo> {
        return todoDao.getNoteById(id)
    }

    suspend fun addNote(todo : Todo){
        todoDao.addNote(todo)
    }

    suspend fun deleteNote(todo : Todo){
        todoDao.deleteNote(todo)
    }



    
}