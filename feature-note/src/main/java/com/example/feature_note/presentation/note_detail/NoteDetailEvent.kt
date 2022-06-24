package com.example.feature_note.presentation.note_detail

sealed class NoteDetailEvent {
    object DeleteNote : NoteDetailEvent()
}