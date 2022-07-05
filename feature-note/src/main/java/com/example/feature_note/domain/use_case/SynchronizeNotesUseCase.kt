package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.repository.NoteRepository

class SynchronizeNotesUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke() = repository.synchronizeNotes()

}