package com.example.notes.presentation.note_add

import androidx.lifecycle.*
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun getOneNote(id: Int): LiveData<Note> {
        return repository.getOneNote(id).asLiveData()
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }
}