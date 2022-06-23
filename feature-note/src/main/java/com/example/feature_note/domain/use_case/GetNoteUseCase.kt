package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(id: Int) = repository.getOneNote(id)
}