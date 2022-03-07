package com.example.notes.data.repository


import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) {
        if (note.title.isEmpty()) {
            dao.insertNote(
                Note(
                    title = "New note",
                    content = note.content,
                    color = note.color
                )
            )
        } else {
            dao.insertNote(note)
        }
    }

    override suspend fun updateNote(note: Note) {
        if (note.title.isEmpty()) {
            dao.updateNote(
                Note(
                    id = note.id,
                    title = "New note",
                    content = note.content,
                    color = note.color
                )
            )
        } else {
            dao.updateNote(note)
        }
    }

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override fun getOneNote(id: Int) = dao.getOneNote(id)

    override fun searchDatabase(searchQuery: String) = dao.searchDatabase(searchQuery)

    override fun getAllNotesByAsc() = dao.getAllNotesByAsc()

    override fun getAllNotesByDesc() = dao.getAllNotesByDesc()

    override fun getAllNotes() = dao.getAllNotes()
}