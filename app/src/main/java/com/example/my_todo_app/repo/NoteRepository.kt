package com.example.my_todo_app.repo

import androidx.lifecycle.LiveData
import com.example.my_todo_app.db.NoteDao
import com.example.my_todo_app.model.Note

class NoteRepository(
    private val noteDao : NoteDao
) {

    val notes : LiveData<List<Note>> = noteDao.getNotes()

    suspend fun addNote(note : Note){
        noteDao.addNote(note)
    }

    suspend fun deleteNote(note : Note){
        noteDao.deleteNote(note)
    }

}