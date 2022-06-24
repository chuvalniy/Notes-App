package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
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

    fun onEvent(noteDetailEvent: NoteDetailEvent) {
        when (noteDetailEvent) {
            is NoteDetailEvent.DeleteNote -> {
                viewModelScope.launch {
                    note?.let {
                        deleteNoteUseCase(note)
                    }
                }
            }
        }
    }

}