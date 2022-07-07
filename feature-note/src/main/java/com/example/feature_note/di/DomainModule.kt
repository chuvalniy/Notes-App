package com.example.feature_note.di

import com.example.feature_note.domain.use_case.*
import org.koin.dsl.module

val noteDomainModule = module {

    factory {
        DeleteNoteUseCase(repository = get())
    }

    factory {
        GetFilteredNotesUseCase(repository = get())
    }

    factory {
        InsertNoteUseCase(repository = get())
    }

    factory {
        RestoreDeletedNoteUseCase(repository = get())
    }

    factory {
        SynchronizeNotesUseCase(repository = get())
    }

    factory {
        UpdateNoteUseCase(repository = get())
    }

    factory {
        ValidateTitleUseCase()
    }
}