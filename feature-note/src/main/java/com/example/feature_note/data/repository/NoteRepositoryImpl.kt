package com.example.feature_note.data.repository


import com.example.feature_note.data.local.NoteDao
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.utils.SortType
import kotlinx.coroutines.flow.Flow

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
        searchQuery: String,
        sortType: SortType
    ): Flow<List<Note>> {
        return dao.getAllNotes(searchQuery, sortType)
    }

}