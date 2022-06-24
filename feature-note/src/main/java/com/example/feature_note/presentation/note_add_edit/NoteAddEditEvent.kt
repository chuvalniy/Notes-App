package com.example.feature_note.presentation.note_add_edit

sealed class NoteAddEditEvent {
    object NoteSubmitted : NoteAddEditEvent()
    data class TitleChanged(val title: String): NoteAddEditEvent()
    data class ContentChanged(val content: String): NoteAddEditEvent()
}