package com.example.feature_note.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val sortType = MutableStateFlow(SortType.ASCENDING)

    fun onEvent(noteListEvent: NoteListEvent) {
        when (noteListEvent) {
            is NoteListEvent.SearchNote -> searchQuery.value = noteListEvent.query
            is NoteListEvent.SortNote -> sortType.value = noteListEvent.sortType
        }
    }

    private val notesFlow = combine(searchQuery, sortType) { query, sort ->
        Pair(query, sort)
    }.flatMapLatest { (searchQuery, sortType) -> repository.getAllNotes(searchQuery, sortType) }

    val notes = notesFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}
