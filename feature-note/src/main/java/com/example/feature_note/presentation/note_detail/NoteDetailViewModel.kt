package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.Resource
import com.example.common.utils.StateStringPropertyDelegate
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class NoteDetailViewModel(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    state: SavedStateHandle
) : ViewModel() {

    private val note = state.get<Note>("note")

    var noteTitle by StateStringPropertyDelegate(state, "title", note?.title, "")
    var noteContent by StateStringPropertyDelegate(state, "content", note?.content, "")

    private val _noteDetailsChannel = Channel<UiNoteDetailsEvent>()
    val noteDetailsEvent get() = _noteDetailsChannel.receiveAsFlow()

    fun onEvent(noteDetailEvent: NoteDetailEvent) {
        when (noteDetailEvent) {
            is NoteDetailEvent.NoteDeleted -> {
                viewModelScope.launch {
                    note?.let { note ->
                        deleteNoteUseCase(note).onEach { event ->
                            when (event) {
                                is Resource.Error -> showSnackbar(event.error ?: "")
                                else -> Unit
                            }
                        }.launchIn(this)
                        _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToListScreen)
                    }
                }
            }
            is NoteDetailEvent.EditButtonClicked -> {
                viewModelScope.launch {
                    note?.let { note ->
                        _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateToAddEditScreen(note))
                    }
                }
            }
            is NoteDetailEvent.BackButtonClicked -> {
                viewModelScope.launch { _noteDetailsChannel.send(UiNoteDetailsEvent.NavigateBack) }
            }
        }
    }

    private fun showSnackbar(text: String) = viewModelScope.launch {
        _noteDetailsChannel.send(UiNoteDetailsEvent.ShowSnackbar(text))
    }


    sealed class UiNoteDetailsEvent {
        data class NavigateToAddEditScreen(val note: Note) : UiNoteDetailsEvent()
        data class ShowSnackbar(val message: String) : UiNoteDetailsEvent()
        object NavigateBack : UiNoteDetailsEvent()
        object NavigateToListScreen : UiNoteDetailsEvent()
    }
}