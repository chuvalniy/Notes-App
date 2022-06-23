package com.example.feature_note.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.domain.use_case.GetAllNotesUseCase
import com.example.feature_note.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val sortType = MutableStateFlow(SortType.ASCENDING)

    fun onEvent(noteListEvent: NoteListEvent) {
        when (noteListEvent) {
            is NoteListEvent.SearchNote -> searchQuery.value = noteListEvent.query
            is NoteListEvent.SortNote -> sortType.value = noteListEvent.sortType
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notesFlow = combine(searchQuery, sortType) { query, sort ->
        Pair(query, sort)
    }.flatMapLatest { (searchQuery, sortType) -> getAllNotesUseCase(searchQuery, sortType) }

    val notes = notesFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}
