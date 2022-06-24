package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    private val note = state.get<Note>("note")

    var noteTitle = state.get<String>("title") ?: note?.title ?: ""
        set(value) {
            field = value
            state.set("title", value)
        }

    var noteContent = state.get<String>("content") ?: note?.content ?: ""
        set(value) {
            field = value
            state.set("content", value)
        }

    private val _noteDetailsChannel = Channel<UiNoteDetailsEvent>()
    val noteDetailsEvent get() = _noteDetailsChannel.receiveAsFlow()

    fun onEvent(noteDetailEvent: NoteDetailEvent) {
        when (noteDetailEvent) {
            is NoteDetailEvent.NoteDeleted -> {
                viewModelScope.launch {
                    note?.let {
                        deleteNoteUseCase(note)
                        _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToListScreen)
                    }
                }
            }
            is NoteDetailEvent.EditButtonClicked -> {
                viewModelScope.launch {
                    note?.let {
                        _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToDetailsScreen(note))
                    }
                }
            }
            is NoteDetailEvent.BackButtonClicked -> {
                viewModelScope.launch { _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateBack) }
            }
        }
    }

    sealed class UiNoteDetailsEvent {
        data class NavigateToDetailsScreen(val note: Note) : UiNoteDetailsEvent()
        object NavigateBack: UiNoteDetailsEvent()
        object NavigateToListScreen: UiNoteDetailsEvent()
    }
}