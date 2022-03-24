package com.noteapp.notes.domain.repository

import com.noteapp.notes.domain.model.Note
import com.noteapp.notes.core.utils.SortType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(sortType: SortType, queryText: String): Flow<List<Note>>

    fun getOneNote(id: Int): Flow<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}