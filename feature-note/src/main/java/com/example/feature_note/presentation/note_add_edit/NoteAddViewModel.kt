package com.example.feature_note.presentation.note_add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.InsertNoteUseCase
import com.example.feature_note.domain.use_case.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var noteColor = state.get<String>("color") ?: note?.color ?: Note.colors.random()
        set(value) {
            field = value
            state.set("color", value)
        }

    fun onEvent(noteAddEvent: NoteAddEvent) {
        viewModelScope.launch {
            when (noteAddEvent) {
                is NoteAddEvent.SaveNote -> {
                    // create use case for validation
                    if (note != null) {
                        val updatedNote = note.copy(
                            title = noteTitle,
                            content = noteContent,
                        )
                        updateNoteUseCase(updatedNote)
                    } else {
                        val newNote = Note(
                            title = noteTitle,
                            content = noteContent,
                            color = noteColor
                        )
                        insertNoteUseCase(newNote)
                    }
                }
            }
        }
    }
}