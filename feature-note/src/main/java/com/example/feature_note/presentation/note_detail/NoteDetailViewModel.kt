package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.*
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.feature_note.domain.use_case.GetNoteUseCase
import dagger.assisted.Assisted
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

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

}