package com.example.feature_note.data.repository


import com.example.feature_note.data.local.NoteDao
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.utils.SortType

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

    override fun getOneNote(id: Int) = dao.getOneNote(id)

}