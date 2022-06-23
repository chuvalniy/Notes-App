package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {

        if (note.title.isEmpty()) {
            repository.updateNote(
                Note(
                    id = note.id,
                    title = "New note",
                    content = note.content,
                    color = note.color
                )
            )
        } else {
            repository.updateNote(note)
        }
    }
}