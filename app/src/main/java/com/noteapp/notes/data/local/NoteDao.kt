package com.noteapp.notes.data.local

import androidx.room.*
import com.noteapp.notes.domain.model.Note
import com.noteapp.notes.core.utils.SortType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    fun getAllNotes(
        sortType: SortType,
        queryText: String
    ): Flow<List<Note>> =
        when(sortType) {
            SortType.ASCENDING -> getAllNotesByAsc(queryText)
            SortType.DESCENDING -> getAllNotesByDesc(queryText)
        }

    @Query("SELECT * FROM note_db WHERE title LIKE '%' || :queryText || '%' ORDER BY timestamp DESC")
    fun getAllNotesByDesc(queryText: String): Flow<List<Note>>

    @Query("SELECT * FROM note_db WHERE title LIKE '%' || :queryText || '%' ORDER BY timestamp ASC")
    fun getAllNotesByAsc(queryText: String): Flow<List<Note>>

    @Query("SELECT * FROM note_db WHERE id =:id")
    fun getOneNote(id: Int): Flow<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}