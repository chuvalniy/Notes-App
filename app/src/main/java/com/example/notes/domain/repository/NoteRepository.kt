package com.example.notes.domain.repository

import androidx.lifecycle.LiveData
import com.example.notes.domain.model.Note

interface NoteRepository {

    fun getAllNotes(): LiveData<List<Note>>

    suspend fun getOneNote(id: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}