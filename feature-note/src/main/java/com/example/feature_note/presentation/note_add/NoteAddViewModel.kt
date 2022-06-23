package com.example.feature_note.presentation.note_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.domain.use_case.GetNoteUseCase
import com.example.feature_note.domain.use_case.InsertNoteUseCase
import com.example.feature_note.domain.use_case.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : ViewModel() {

    fun onEvent(noteAddEvent: NoteAddEvent) {
        viewModelScope.launch {
            when (noteAddEvent) {
                is NoteAddEvent.InsertNote -> insertNoteUseCase(noteAddEvent.note)
                is NoteAddEvent.UpdateNote -> updateNoteUseCase(noteAddEvent.note)
            }
        }
    }

    fun getOneNote(id: Int): LiveData<Note> {
        return getNoteUseCase(id).asLiveData()
    }
}