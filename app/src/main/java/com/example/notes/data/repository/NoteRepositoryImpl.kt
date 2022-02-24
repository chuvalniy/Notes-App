package com.example.notes.data.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun getOneNote(id: Int) = dao.getOneNote(id)

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override suspend fun updateNote(note: Note) = dao.updateNote(note)

    override fun getAllNotes() = dao.getAllNotes()

}