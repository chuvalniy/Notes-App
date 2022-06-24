package com.example.feature_note.presentation.note_detail

sealed class NoteDetailEvent {
    object EditButtonClicked : NoteDetailEvent()
    object BackButtonClicked : NoteDetailEvent()
    object NoteDeleted : NoteDetailEvent()
}