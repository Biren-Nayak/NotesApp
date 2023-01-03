package com.example.notesapp.network

import com.example.notesapp.model.FirebaseNoteModel
import com.example.notesapp.model.Note
import com.example.notesapp.utils.Constants.COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class FirebaseDatasource @Inject constructor(private val firestore: FirebaseFirestore) {

    fun add(note: Note, add:(String) -> Unit) = firestore.collection(COLLECTION_NAME)
        .add(FirebaseNoteModel(note.title, note.data)).addOnSuccessListener {
        add(it.id)
    }

    fun update(note: Note) = note.firestoreId?.let{
        firestore.collection(COLLECTION_NAME).document(note.firestoreId).set(
            FirebaseNoteModel(note.title, note.data)
        )
    }


    fun delete(note: Note) = note.firestoreId?.let {
        firestore.collection(COLLECTION_NAME).document(it).delete()
    }
}