package com.example.notes.presentation.note_list

import com.example.notes.domain.model.Note
import com.example.notes.domain.util.SortType

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val sortType: SortType = SortType.Descending
)
