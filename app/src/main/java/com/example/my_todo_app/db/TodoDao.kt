package com.example.my_todo_app.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.my_todo_app.model.Todo


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(todo : Todo)

    @Delete
    suspend fun deleteNote(todo : Todo)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getNotes() : LiveData<List<Todo>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id : Int) : LiveData<Todo>

}