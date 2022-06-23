package com.example.feature_note.presentation.note_add

import com.example.feature_note.domain.model.Note

sealed class NoteAddEvent {
    data class UpdateNote(val note: Note): NoteAddEvent()
    data class InsertNote(val note: Note): NoteAddEvent()
}