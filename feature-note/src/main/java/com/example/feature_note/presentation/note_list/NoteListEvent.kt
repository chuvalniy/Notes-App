package com.example.feature_note.presentation.note_list

import com.example.feature_note.utils.SortType

sealed class NoteListEvent {
    data class SearchNote(val query: String) : NoteListEvent()
    data class SortNote(val sortType: SortType) : NoteListEvent()
}