package com.example.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao
}