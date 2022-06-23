package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.utils.SortType

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        searchQuery: String,
        sortType: SortType
    ) = repository.getAllNotes(searchQuery, sortType)
}