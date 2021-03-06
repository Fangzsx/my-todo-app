package com.example.my_todo_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val content : String,
    val isDone : Boolean = false)