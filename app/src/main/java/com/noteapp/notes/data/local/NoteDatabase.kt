package com.noteapp.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.noteapp.notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao
}