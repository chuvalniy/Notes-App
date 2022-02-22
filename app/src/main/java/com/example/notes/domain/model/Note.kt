package com.example.notes.domain.model

import com.example.notes.data.local.entity.NoteEntity

data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
    val isImportant: Boolean
)  {
    fun toNoteEntity(): NoteEntity {
        return NoteEntity(
            title = title,
            content = content,
            color = color,
            timestamp = timestamp,
            isImportant = isImportant
        )
    }
}
