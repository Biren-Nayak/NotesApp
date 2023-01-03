package com.example.notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.network.FirebaseDatasource
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.room.NotesDao
import com.example.notesapp.room.NotesDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun myRepoImpl(notesDao: NotesDao, source: FirebaseDatasource) = NoteRepository(notesDao, source)


    @Provides
    @Singleton
    fun myDaoImpl(database: NotesDatabase) = database.notesDao()


    @Provides
    @Singleton
    fun myDatabaseImpl(application: Application) = Room
            .databaseBuilder(application, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun myFirebaseImpl() = Firebase.firestore

}

