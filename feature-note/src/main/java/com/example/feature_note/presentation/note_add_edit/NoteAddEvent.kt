package com.example.feature_note.presentation.note_add_edit

sealed class NoteAddEvent {
    object SaveNote : NoteAddEvent()
}