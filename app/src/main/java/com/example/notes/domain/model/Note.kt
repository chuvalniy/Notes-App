package com.example.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_db")
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
)
