package com.example.feature_note.di

import androidx.room.Room
import com.example.feature_note.data.local.cache.NoteDatabase
import com.example.feature_note.data.local.settings.PreferencesManager
import com.example.feature_note.data.local.settings.PreferencesManagerImpl
import com.example.feature_note.data.remote.NoteFirestore
import com.example.feature_note.data.remote.NoteFirestoreImpl
import com.example.feature_note.data.repository.NoteRepositoryImpl
import com.example.feature_note.domain.repository.NoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val noteDataModule = module {

    single<PreferencesManager> {
        PreferencesManagerImpl(context = androidContext())
    }

    single<NoteFirestore> {
        NoteFirestoreImpl(userSessionStorage = get())
    }

    single<NoteRepository> {
        NoteRepositoryImpl(api = get(), db = get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }
}