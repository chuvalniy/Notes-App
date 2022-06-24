package com.example.feature_note.presentation.note_list

import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.model.Note


sealed class NoteListEvent {
    data class SearchNote(val query: String) : NoteListEvent()
    data class SortNote(val sortType: SortType) : NoteListEvent()
    data class SwipeNote(val note: Note): NoteListEvent()
    object RestoreDeletedNote: NoteListEvent()
}