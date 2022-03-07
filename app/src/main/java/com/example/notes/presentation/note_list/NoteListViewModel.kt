package com.example.notes.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.notes.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val notes = repository.getAllNotes().asLiveData()

    val notesByAscending = repository.getAllNotesByAsc().asLiveData()
    val notesByDescending = repository.getAllNotesByDesc().asLiveData()

    fun searchDatabase(searchQuery: String) = repository.searchDatabase(searchQuery).asLiveData()
}