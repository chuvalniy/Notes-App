package com.example.feature_note.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.feature_note.data.local.cache.model.NoteCacheEntity

@Database(
    entities = [NoteCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao

    companion object {
        const val DATABASE_NAME = "note_db"
    }
}