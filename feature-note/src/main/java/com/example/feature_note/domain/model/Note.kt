package com.example.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_note.R

@Entity(tableName = "note_db")
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int? = null,
    val timestamp: Long = System.currentTimeMillis() / 1000L
) {
    companion object {
        val colors = listOf(
            R.color.light_red,
            R.color.light_blue,
            R.color.light_green,
            R.color.light_purple,
            R.color.light_orange
        )
    }
}
