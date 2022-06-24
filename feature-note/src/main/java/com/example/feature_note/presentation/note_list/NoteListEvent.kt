package com.example.feature_note.presentation.note_list

import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note


sealed class NoteListEvent {
    data class QueryEntered(val query: String) : NoteListEvent()
    data class SortOrderSelected(val sortType: SortType) : NoteListEvent()
    data class NoteSwiped(val note: Note) : NoteListEvent()
    data class DeletedNoteRestored(val note: Note) : NoteListEvent()
    data class NoteSelected(val note: Note) : NoteListEvent()
    object AddNewNoteClicked : NoteListEvent()
    object SortButtonClicked : NoteListEvent()
}