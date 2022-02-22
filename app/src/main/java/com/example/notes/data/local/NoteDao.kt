package com.example.notes.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.data.local.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_db")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM note_db WHERE id =:id")
    suspend fun getOneNote(id: Int): NoteEntity

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}