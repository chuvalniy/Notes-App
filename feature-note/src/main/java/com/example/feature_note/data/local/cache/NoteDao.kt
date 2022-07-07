package com.example.feature_note.data.local.cache

import androidx.room.*
import com.example.feature_note.data.local.cache.model.NoteCacheEntity
import com.example.feature_note.data.local.settings.SortType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    fun getFilteredNotes(searchQuery: String, sortType: SortType): Flow<List<NoteCacheEntity>> =
        when (sortType) {
            SortType.ASCENDING -> {
                getNotesByAscending(searchQuery)
            }
            SortType.DESCENDING -> {
                getNotesByDescending(searchQuery)
            }
        }

    @Query("SELECT * FROM note_db WHERE title LIKE '%' || :searchQuery || '%' ORDER BY timestamp ASC")
    fun getNotesByAscending(searchQuery: String): Flow<List<NoteCacheEntity>>

    @Query("SELECT * FROM note_db WHERE title LIKE '%' || :searchQuery || '%' ORDER BY timestamp DESC")
    fun getNotesByDescending(searchQuery: String): Flow<List<NoteCacheEntity>>

    @Query("SELECT * FROM note_db")
    suspend fun getAllNotes(): List<NoteCacheEntity>

    @Delete
    suspend fun deleteNote(note: NoteCacheEntity)

    @Insert
    suspend fun insertNote(note: NoteCacheEntity)

    @Update
    suspend fun updateNote(note: NoteCacheEntity)

    @Query("SELECT * FROM note_db WHERE id = :id")
    suspend fun getNoteById(id: String): NoteCacheEntity?
}