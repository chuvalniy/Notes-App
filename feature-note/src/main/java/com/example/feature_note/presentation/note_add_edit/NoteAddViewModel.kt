package com.example.feature_note.presentation.note_add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.InsertNoteUseCase
import com.example.feature_note.domain.use_case.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val state: SavedStateHandle
) : ViewModel() {

    val note = state.get<Note>("note")

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

    private var noteColor = state.get<String>("color") ?: note?.color ?: Note.colors.random()
        set(value) {
            field = value
            state.set("color", value)
        }

    private val _noteAddEditChannel = Channel<UiAddEditEvent>()
    val noteAddEditEvent get() = _noteAddEditChannel.receiveAsFlow()

    fun onEvent(event: NoteAddEditEvent) {
        when (event) {
            is NoteAddEditEvent.TitleChanged -> {
                noteTitle = event.title
            }
            is NoteAddEditEvent.ContentChanged -> {
                noteContent = event.content
            }
            is NoteAddEditEvent.BackButtonClicked -> {
                viewModelScope.launch { _noteAddEditChannel.send(UiAddEditEvent.NavigateBack) }
            }
            is NoteAddEditEvent.NoteSubmitted -> {
                if (noteTitle.isEmpty()) {
                    showInvalidInputMessage("The title can't be blank")
                    return
                }

                if (note != null) {
                    val updatedNote = note.copy(
                        title = noteTitle,
                        content = noteContent,
                    )
                    updateNote(updatedNote)
                } else {
                    val newNote = Note(
                        title = noteTitle,
                        content = noteContent,
                        color = noteColor
                    )
                    insertNote(newNote)
                }
            }
        }
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        updateNoteUseCase(note)
        _noteAddEditChannel.send(UiAddEditEvent.NavigateToListScreen)
    }

    private fun insertNote(note: Note) = viewModelScope.launch {
        insertNoteUseCase(note)
        _noteAddEditChannel.send(UiAddEditEvent.NavigateToListScreen)
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        _noteAddEditChannel.send(UiAddEditEvent.ShowSnackbar(text))
    }

    sealed class UiAddEditEvent {
        data class ShowSnackbar(val message: String) : UiAddEditEvent()
        object NavigateToListScreen : UiAddEditEvent()
        object NavigateBack : UiAddEditEvent()
    }
}