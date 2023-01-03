package com.example.notesapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.model.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NotesDatabase: RoomDatabase(){
    abstract fun notesDao(): NotesDao
}