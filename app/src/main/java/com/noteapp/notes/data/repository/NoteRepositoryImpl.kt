package com.noteapp.notes.data.repository


import com.noteapp.notes.data.local.NoteDao
import com.noteapp.notes.domain.model.Note
import com.noteapp.notes.domain.repository.NoteRepository
import com.noteapp.notes.core.utils.SortType

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

    override fun getAllNotes(
        sortType: SortType,
        queryText: String
    ) = dao.getAllNotes(sortType, queryText)

}