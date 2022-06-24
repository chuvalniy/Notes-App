package com.example.feature_note.data.repository


import com.example.feature_note.data.local.cache.NoteDao
import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNotes(
        searchQuery: String,
        sortType: SortType
    ) = dao.getAllNotes(searchQuery, sortType)

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun updateNote(note: Note) = dao.updateNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

}