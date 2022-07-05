package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    operator fun invoke(note: Note) = repository.deleteNote(note)
}