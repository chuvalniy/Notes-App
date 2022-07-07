package com.example.feature_note.data.repository


import android.util.Log
import com.example.feature_note.data.local.cache.NoteDatabase
import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.data.mapper.toNoteCacheEntity
import com.example.feature_note.data.mapper.toNoteDomain
import com.example.feature_note.data.mapper.toNoteRemoteEntity
import com.example.feature_note.data.remote.NoteFirestore
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val api: NoteFirestore,
    db: NoteDatabase
) : NoteRepository {

    private val dao = db.dao

    override fun getFilteredNotes(query: String, sortType: SortType): Flow<List<Note>> {
        val localData = dao.getFilteredNotes(query, sortType)
        return localData.map { it.map { notes -> notes.toNoteDomain() } }
    }

    override suspend fun synchronizeNotes() {

        try {
            val deletedRemoteNotes = api.getDeletedNotes()

            deletedRemoteNotes.onEach { remoteNote ->
                val noteCacheEntity = remoteNote.toNoteCacheEntity()
                dao.deleteNote(noteCacheEntity)
            }

            val remoteNotes = api.getAllNotes()
            val localNotes = dao.getAllNotes() as ArrayList

            remoteNotes.onEach { remoteNote ->
                dao.getNoteById(remoteNote.id)?.let { cachedNote ->
                    localNotes.remove(cachedNote)
                } ?: dao.insertNote(remoteNote.toNoteCacheEntity())
            }
        } catch (e: Exception) {
            Log.d("RepositoryException", e.localizedMessage ?: "Error")
        }
    }

    override suspend fun insertNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.insertNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.insertNote(remoteEntity)

        } catch (e: Exception) {
            Log.d("RepositoryException", e.localizedMessage ?: "Error")
        }
    }

    override suspend fun restoreDeletedNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.insertNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.insertNote(remoteEntity)
            api.deleteDeletedNote(remoteEntity)
        } catch (e: Exception) {
            Log.d("RepositoryException", e.localizedMessage ?: "Error")
        }
    }

    override suspend fun deleteNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.deleteNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.deleteNote(remoteEntity)
            api.insertDeletedNote(remoteEntity)
        } catch (e: Exception) {
            Log.d("RepositoryException", e.localizedMessage ?: "Error")
        }
    }

    override suspend fun updateNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.updateNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.updateNote(remoteEntity)
        } catch (e: Exception) {
            Log.d("RepositoryException", e.localizedMessage ?: "Error")
        }
    }
}