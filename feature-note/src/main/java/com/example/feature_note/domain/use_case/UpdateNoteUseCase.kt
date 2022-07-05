package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(note: Note) = repository.updateNote(note)
}