package com.example.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.data.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteDao
}