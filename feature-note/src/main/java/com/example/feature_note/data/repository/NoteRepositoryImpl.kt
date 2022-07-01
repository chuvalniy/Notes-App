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

class NoteRepositoryImpl(
    private val api: NoteFirestore,
    private val db: NoteDatabase
) : NoteRepository {

    private val dao = db.dao

    override suspend fun synchronizeNotes() {
        val remoteNotes = api.getAllNotes()
        val localNotes = dao.getAllNotes().toMutableList()

        for(note in remoteNotes) {
            dao.getNoteById(note.id)?.let { cachedNote ->
                localNotes.remove(cachedNote)
            } ?: dao.insertNote(note.toNoteCacheEntity())
        }
    }

    override fun getFilteredNotes(query: String, sortType: SortType): Flow<List<Note>> {
        val localData = dao.getFilteredNotes(query, sortType)
        return localData.map { it.map { notes -> notes.toNoteDomain() } }
    }


    override suspend fun insertNote(note: Note) {
        val cacheEntity = note.toNoteCacheEntity()
        dao.insertNote(cacheEntity)

        val remoteEntity = cacheEntity.toNoteRemoteEntity()
        api.insertNote(remoteEntity)
    }

    override suspend fun deleteNote(note: Note) {
        val cacheEntity = note.toNoteCacheEntity()
        dao.deleteNote(cacheEntity)

        val remoteEntity = cacheEntity.toNoteRemoteEntity()
        api.deleteNote(remoteEntity)
    }

    override suspend fun updateNote(note: Note) {
        val cacheEntity = note.toNoteCacheEntity()
        dao.updateNote(cacheEntity)

        val remoteEntity = cacheEntity.toNoteRemoteEntity()
        api.updateNote(remoteEntity)
    }
}