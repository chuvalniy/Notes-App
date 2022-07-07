package com.example.feature_note.data.remote

import com.example.common.settings.UserSessionStorage
import com.example.common.utils.Constants
import com.example.feature_note.data.remote.model.NoteRemoteEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NoteFirestoreImpl(
    private val userSessionStorage: UserSessionStorage
) : NoteFirestore {

    private val firestore = FirebaseFirestore.getInstance()
    private val userId get() = userSessionStorage.getUserSessionId()

    override suspend fun getAllNotes(): List<NoteRemoteEntity> {
        return firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(userId)
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .get()
            .await()
            .toObjects(NoteRemoteEntity::class.java)
    }

    override suspend fun insertNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(userId)
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .set(noteRemoteEntity)
            .await()
    }

    override suspend fun deleteNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(userId)
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .delete()
            .await()
    }

    override suspend fun updateNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(userId)
            .collection(Constants.FIRE_STORE_NOTE_TABLE)
            .document(noteRemoteEntity.id)
            .set(noteRemoteEntity)
            .await()
    }


    override suspend fun insertDeletedNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .document(userId)
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .document(noteRemoteEntity.id)
            .set(noteRemoteEntity)
            .await()
    }

    override suspend fun deleteDeletedNote(noteRemoteEntity: NoteRemoteEntity) {
        firestore
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .document(userId)
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .document(noteRemoteEntity.id)
            .delete()
            .await()
    }

    override suspend fun getDeletedNotes(): List<NoteRemoteEntity> {
        return firestore
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .document(userId)
            .collection(Constants.FIRE_STORE_DELETED_NOTES)
            .get()
            .await()
            .toObjects(NoteRemoteEntity::class.java)
    }
}