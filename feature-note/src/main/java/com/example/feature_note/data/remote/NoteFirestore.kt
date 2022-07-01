package com.example.feature_note.data.remote

import com.example.feature_note.data.remote.model.NoteRemoteEntity

interface NoteFirestore {

    suspend fun getAllNotes(): List<NoteRemoteEntity>

    suspend fun insertNote(noteRemoteEntity: NoteRemoteEntity)

    suspend fun deleteNote(noteRemoteEntity: NoteRemoteEntity)

    suspend fun updateNote(noteRemoteEntity: NoteRemoteEntity)
}