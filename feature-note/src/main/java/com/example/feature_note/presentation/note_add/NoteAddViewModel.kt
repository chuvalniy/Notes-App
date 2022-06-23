package com.example.feature_note.presentation.note_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun onEvent(noteAddEvent: NoteAddEvent) {
        viewModelScope.launch {
            when (noteAddEvent) {
                is NoteAddEvent.InsertNote -> repository.insertNote(noteAddEvent.note)
                is NoteAddEvent.UpdateNote -> repository.updateNote(noteAddEvent.note)
            }
        }
    }

    fun getOneNote(id: Int): LiveData<Note> {
        return repository.getOneNote(id).asLiveData()
    }
}