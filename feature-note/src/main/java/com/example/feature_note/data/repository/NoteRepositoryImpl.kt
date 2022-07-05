package com.example.feature_note.data.repository


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
import java.io.IOException

class NoteRepositoryImpl(
    private val api: NoteFirestore,
    db: NoteDatabase
) : NoteRepository {

    private val dao = db.dao

    override suspend fun synchronizeNotes() {
        try {
            val remoteNotes = api.getAllNotes()
            val localNotes = dao.getAllNotes().toMutableList()

            for (note in remoteNotes) {
                dao.getNoteById(note.id)?.let { cachedNote ->
                    localNotes.remove(cachedNote)
                } ?: dao.insertNote(note.toNoteCacheEntity())
            }
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }

    override suspend fun synchronizeDeletedNotes() {
        try {
            val remoteNotes = api.getDeletedNotes()

            for (note in remoteNotes) {
                dao.deleteNote(note.toNoteCacheEntity())
            }
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }

    override fun getFilteredNotes(query: String, sortType: SortType): Flow<List<Note>> {
        val localData = dao.getFilteredNotes(query, sortType)
        return localData.map { it.map { notes -> notes.toNoteDomain() } }
    }


    override suspend fun insertNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.insertNote(cacheEntity)
            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.insertNote(remoteEntity)
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }

    override suspend fun restoreDeletedNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.insertNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.insertNote(remoteEntity)
            api.deleteDeletedNote(remoteEntity)
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }

    override suspend fun deleteNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.deleteNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.deleteNote(remoteEntity)
            api.insertDeletedNote(remoteEntity)
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }

    override suspend fun updateNote(note: Note) {
        try {
            val cacheEntity = note.toNoteCacheEntity()
            dao.updateNote(cacheEntity)

            val remoteEntity = cacheEntity.toNoteRemoteEntity()
            api.updateNote(remoteEntity)
        } catch (e: IOException) {

        } catch (e: Exception) {

        }
    }
}