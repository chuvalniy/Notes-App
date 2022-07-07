package com.example.feature_note.data.mapper

import com.example.feature_note.data.local.cache.model.NoteCacheEntity
import com.example.feature_note.data.remote.model.NoteRemoteEntity
import com.example.feature_note.domain.model.Note
import java.util.*

fun NoteRemoteEntity.toNoteCacheEntity(): NoteCacheEntity {
    return NoteCacheEntity(
        id = id,
        title = title,
        content = content,
        color = color,
        timestamp = timestamp
    )
}

fun NoteCacheEntity.toNoteDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )
}

fun Note.toNoteCacheEntity(): NoteCacheEntity {
    return NoteCacheEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )
}

fun NoteCacheEntity.toNoteRemoteEntity(): NoteRemoteEntity {
    return NoteRemoteEntity(
        id = id,
        title = title,
        content = content,
        color = color,
        timestamp = timestamp
    )
}