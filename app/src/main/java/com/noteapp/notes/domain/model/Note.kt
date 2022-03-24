package com.noteapp.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noteapp.notes.R

@Entity(tableName = "note_db")
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int? = null,
    val timestamp: Long = System.currentTimeMillis() / 1000L
) {
    companion object {
        val colors = arrayOf(
            R.color.light_red,
            R.color.light_blue,
            R.color.light_green,
            R.color.light_purple,
            R.color.light_orange
        )
    }
}
