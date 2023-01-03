package com.example.notesapp.repository

import com.example.notesapp.model.Note
import com.example.notesapp.network.FirebaseDatasource
import com.example.notesapp.room.NotesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val notesDao: NotesDao,
    private val source: FirebaseDatasource,
) {

    fun readAll() = notesDao.getAllNotes()

    suspend fun create(note: Note) {

        if (note.firestoreId == null){
            source.add(note) {
                CoroutineScope(Dispatchers.IO).launch {
                    notesDao.upsertNote(note.copy(firestoreId = it))
                }
            }
        }else
            source.update(note).also { notesDao.upsertNote(note) }

    }

    suspend fun delete(note: Note) = notesDao.deleteNote(note).also { source.delete(note) }

}