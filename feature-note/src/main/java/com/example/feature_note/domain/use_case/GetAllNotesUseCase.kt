package com.example.feature_note.domain.use_case

import com.example.feature_note.data.local.settings.SortType
import com.example.feature_note.domain.repository.NoteRepository

class GetAllNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(
        query: String,
        sortType: SortType
    ) = repository.getFilteredNotes(query, sortType)
}