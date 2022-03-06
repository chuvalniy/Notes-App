package com.example.notes.domain.repository

import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getOneNote(id: Int): Flow<Note>

    fun searchDatabase(searchQuery: String): Flow<List<Note>>

    fun getAllNotesByAsc(): Flow<List<Note>>

    fun getAllNotesByDesc(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}