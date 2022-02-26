package com.example.notes.domain.repository

import androidx.lifecycle.LiveData
import com.example.notes.domain.model.Note
import com.example.notes.domain.util.SortType
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(sortType: SortType = SortType.Descending): Flow<List<Note>>

    fun getOneNote(id: Int): Flow<Note>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}