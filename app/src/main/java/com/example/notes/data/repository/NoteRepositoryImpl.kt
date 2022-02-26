package com.example.notes.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override suspend fun updateNote(note: Note) = dao.updateNote(note)

    override fun getOneNote(id: Int) = dao.getOneNote(id)

    override fun getAllNotes(sortType: SortType): Flow<List<Note>> {
        return dao.getAllNotes().map { notes ->
            when (sortType) {
                is SortType.Ascending -> {
                    Log.d("Test", sortType.toString())
                    notes.sortedBy { it.timestamp }
                }
                is SortType.Descending -> {
                    Log.d("Test", sortType.toString())
                    notes.sortedByDescending { it.timestamp }
                }
            }
        }
    }

}