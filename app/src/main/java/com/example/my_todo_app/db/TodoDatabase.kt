package com.example.my_todo_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_todo_app.model.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun getNoteDao() : TodoDao

    companion object{
        @Volatile
        private var INSTANCE : TodoDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : TodoDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,
                TodoDatabase::class.java,
                "notes_db")
                    .build()
            }
            return INSTANCE as TodoDatabase
        }
    }

}