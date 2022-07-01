package com.example.feature_note.domain.repository

import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun synchronizeNotes()

    fun getFilteredNotes(query: String, sortType: SortType): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}