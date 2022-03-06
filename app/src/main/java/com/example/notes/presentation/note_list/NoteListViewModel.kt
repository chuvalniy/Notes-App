package com.example.notes.presentation.note_list

import android.util.Log
import androidx.lifecycle.*
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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