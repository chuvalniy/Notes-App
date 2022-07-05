package com.example.feature_note.domain.repository

import com.example.common.utils.Resource
import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun synchronizeNotes(): Flow<Resource<String>>

    fun restoreDeletedNote(note: Note): Flow<Resource<String>>

    fun getFilteredNotes(query: String, sortType: SortType): Flow<List<Note>>

    fun insertNote(note: Note): Flow<Resource<String>>

    fun deleteNote(note: Note): Flow<Resource<String>>

    fun updateNote(note: Note): Flow<Resource<String>>
}