package com.example.feature_note.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao
}