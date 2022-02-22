package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.notes.data.local.NoteDao
import com.example.notes.data.local.entity.asDomainModel
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun getOneNote(id: Int) = dao.getOneNote(id).toNote()

    override suspend fun insertNote(note: Note) = dao.insertNote(note.toNoteEntity())

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note.toNoteEntity())

    override suspend fun updateNote(note: Note) = dao.updateNote(note.toNoteEntity())

    override fun getAllNotes(): LiveData<List<Note>> {
        val data: LiveData<List<Note>> = Transformations.map(dao.getAllNotes()) {
            it.asDomainModel()
        }

        return data
    }
}