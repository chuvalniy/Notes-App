package com.example.notes.data.local

import androidx.room.*
import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_db")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_db WHERE id =:id")
    fun getOneNote(id: Int): Flow<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}