package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.data.local.NoteDatabase
import com.example.notes.data.repository.NoteRepositoryImpl
import com.example.notes.domain.repository.NoteRepository
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