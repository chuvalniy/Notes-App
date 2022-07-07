package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Constants

import com.example.common.utils.StateStringPropertyDelegate
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class NoteDetailViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    state: SavedStateHandle
) : ViewModel() {

    private val note = state.get<Note>("note")

    var noteTitle by StateStringPropertyDelegate(
        state = state,
        key = "title",
        initialValue = Constants.EMPTY_VALUE,
        savedValue = note?.title
    )
    var noteContent by StateStringPropertyDelegate(
        state = state,
        key = "content",
        initialValue = Constants.EMPTY_VALUE,
        savedValue = note?.content,
    )

    private val _noteDetailsChannel = Channel<UiNoteDetailsEvent>()
    val noteDetailsEvent get() = _noteDetailsChannel.receiveAsFlow()

    fun onEvent(noteDetailEvent: NoteDetailEvent) {
        when (noteDetailEvent) {
            is NoteDetailEvent.NoteDeleted -> deleteNote()
            is NoteDetailEvent.EditButtonClicked -> navigateToAddEditScreen()
            is NoteDetailEvent.BackButtonClicked -> navigateBack()
        }
    }

    private fun navigateToAddEditScreen() = viewModelScope.launch {
        note?.let { note ->
            _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToAddEditScreen(note))
        }
    }

    private fun navigateBack() = viewModelScope.launch {
        _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateBack)
    }

    private fun deleteNote() = viewModelScope.launch {
        note?.let { note ->
            deleteNoteUseCase(note)
            _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToListScreen)
        }
    }

    sealed class UiNoteDetailsEvent {
        data class NavigateToAddEditScreen(val note: Note) : UiNoteDetailsEvent()
        object NavigateBack : UiNoteDetailsEvent()
        object NavigateToListScreen : UiNoteDetailsEvent()
    }
}