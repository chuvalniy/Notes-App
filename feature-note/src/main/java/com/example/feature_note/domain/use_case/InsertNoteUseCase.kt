package com.example.feature_note.domain.use_case

import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        if (note.title.isEmpty()) {
            repository.insertNote(
                Note(
                    title = "New note ${note.id}",
                    content = note.content,
                    color = note.color
                )
            )
        } else {
            repository.insertNote(note)
        }
    }
}