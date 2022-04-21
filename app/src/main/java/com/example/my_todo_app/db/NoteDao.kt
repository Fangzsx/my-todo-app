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

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id : Int) : LiveData<Note>

}