package com.example.feature_note.di

import android.app.Application
import androidx.room.Room
import com.example.feature_note.data.local.NoteDatabase
import com.example.feature_note.data.repository.NoteRepositoryImpl
import com.example.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }
}