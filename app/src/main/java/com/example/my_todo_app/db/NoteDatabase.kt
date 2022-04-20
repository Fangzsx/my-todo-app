package com.example.my_todo_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_todo_app.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : NoteDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,
                NoteDatabase::class.java,
                "notes_db")
                    .build()
            }
            return INSTANCE as NoteDatabase
        }
    }

}