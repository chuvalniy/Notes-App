package com.example.feature_note.di

import androidx.lifecycle.SavedStateHandle
import com.example.feature_note.presentation.note_add_edit.NoteAddViewModel
import com.example.feature_note.presentation.note_detail.NoteDetailViewModel
import com.example.feature_note.presentation.note_list.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteAppModule = module {

    viewModel { params ->
        NoteAddViewModel(
            insertNoteUseCase = get(),
            updateNoteUseCase = get(),
            validateTitleUseCase = get(),
            state = params.get()
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        NoteDetailViewModel(
            deleteNoteUseCase = get(),
            state = handle
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        NoteListViewModel(
            getFilteredNotesUseCase = get(),
            deleteNoteUseCase = get(),
            restoreDeletedNoteUseCase = get(),
            preferencesManager = get(),
            state = handle
        )
    }
}