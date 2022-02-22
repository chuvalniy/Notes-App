package com.example.notes.data.local.entity

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.domain.model.Note

@Entity(tableName = "note_db")
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
    val isImportant: Boolean
) {
    fun toNote(): Note {
        return Note(
            title = title,
            content = content,
            color = color,
            timestamp = timestamp,
            isImportant = isImportant
        )
    }
}

fun List<NoteEntity>.asDomainModel(): List<Note> {
    return map {
        Note(
            title = it.title,
            content = it.content,
            color = it.color,
            timestamp = it.timestamp,
            isImportant = it.isImportant
        )
    }
}
