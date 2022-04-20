package com.example.my_todo_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.my_todo_app.model.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM notes")
    fun getNotes() : LiveData<List<Note>>
}