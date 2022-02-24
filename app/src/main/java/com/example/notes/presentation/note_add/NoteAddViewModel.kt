package com.example.notes.presentation.note_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteAddViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _noteItem = MutableLiveData<Note>()
    val noteItem: LiveData<Note> get() = _noteItem

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }
}