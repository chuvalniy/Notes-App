package com.example.notes.data.repository


import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getAllNotes(sortType: SortType): Flow<List<Note>> {
        return dao.getAllNotes().map { notes ->
            when (sortType) {
                is SortType.Ascending -> {
                    notes.sortedBy { it.timestamp }
                }
                is SortType.Descending -> {
                    notes.sortedByDescending { it.timestamp }
                }
            }
        }
    }

}