package com.example.feature_note.data.remote

import com.example.common.utils.Constants
import com.example.feature_note.data.remote.model.NoteRemoteEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class NoteFirestoreImpl : NoteFirestore {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getAllNotes(): List<NoteRemoteEntity> {
        return firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .get()
            .await()
            .toObjects(NoteRemoteEntity::class.java)
    }

    override suspend fun insertNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .set(noteRemoteEntity)
            .await()
    }

    override suspend fun deleteNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .delete()
            .await()
    }

    override suspend fun updateNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .set(noteRemoteEntity)
            .await()
    }
}