package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {

        if (note.title.isEmpty()) {
            repository.updateNote(
                note.copy(title = "New note",)
            )
        } else {
            repository.updateNote(note)
        }
    }
}