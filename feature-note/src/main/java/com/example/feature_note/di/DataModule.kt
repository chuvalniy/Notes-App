package com.example.feature_note.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.feature_note.data.local.cache.NoteDatabase
import com.example.feature_note.data.remote.NoteFirestore
import com.example.feature_note.data.remote.NoteFirestoreImpl
import com.example.feature_note.data.local.settings.PreferencesManager
import com.example.feature_note.data.local.settings.PreferencesManagerImpl
import com.example.feature_note.data.repository.NoteRepositoryImpl
import com.example.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteFirestore(): NoteFirestore {
        return NoteFirestoreImpl()
    }

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManagerImpl(context)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(api: NoteFirestore, db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(api, db)
    }

}