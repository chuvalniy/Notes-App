package com.example.notes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_db")
data class NoteEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
    val isImportant: Boolean
)
