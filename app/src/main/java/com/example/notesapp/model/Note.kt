package com.example.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Note (
    val title: String,
    val data: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val firestoreId: String? = null,
)