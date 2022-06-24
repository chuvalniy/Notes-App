package com.example.feature_note.di

import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository = repository)
    }

    @Provides
    fun provideGetAllNotesUseCase(repository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(repository = repository)
    }

    @Provides
    fun provideInsertNoteUseCase(repository: NoteRepository): InsertNoteUseCase {
        return InsertNoteUseCase(repository = repository)
    }

    @Provides
    fun provideUpdateNoteUseCase(repository: NoteRepository): UpdateNoteUseCase {
        return UpdateNoteUseCase(repository = repository)
    }
}