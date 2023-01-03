package com.example.notesapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesapp.model.Note
import com.example.notesapp.utils.Constants.TABLE_NAME

@Dao
interface NotesDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(note: Note)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    suspend fun getLastNote(): Note

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun getNote(id: Int): Note
}