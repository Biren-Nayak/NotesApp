package com.example.notesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    val notesList: LiveData<List<Note>> = repository.readAll()

    private var _selectedNote = MutableLiveData<Note?>()
    val selectedNote: LiveData<Note?> = _selectedNote


    fun add(note: Note) = viewModelScope.launch { repository.create(note) }


    fun delete(note: Note) = viewModelScope.launch { repository.delete(note) }


    fun onNoteClicked(note: Note) { _selectedNote.value = note }

    fun deselectNote() { _selectedNote.value = null }

}