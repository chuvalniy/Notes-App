package com.example.notes.presentation.note_list

import androidx.lifecycle.ViewModel
import com.example.notes.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val notes = repository.getAllNotes()
}