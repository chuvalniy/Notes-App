package com.example.notes.domain.repository

import androidx.lifecycle.LiveData
import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getOneNote(id: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}