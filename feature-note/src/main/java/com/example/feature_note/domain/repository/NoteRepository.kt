package com.example.feature_note.domain.repository

import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(searchQuery: String, sortType: SortType): Flow<List<Note>>

    fun getOneNote(id: Int): Flow<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}