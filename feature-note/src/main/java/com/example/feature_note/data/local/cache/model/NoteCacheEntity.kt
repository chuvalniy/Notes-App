package com.example.feature_note.data.local.cache.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_note.data.local.cache.NoteDatabase
import kotlinx.parcelize.Parcelize

@Entity(tableName = NoteDatabase.DATABASE_NAME)
@Parcelize
data class NoteCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val content: String,
    val color: String,
    val timestamp: Long
) : Parcelable