package com.example.feature_note.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_note.data.local.settings.PreferencesManager
import com.example.feature_note.domain.use_case.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    val preferencesFlow = preferencesManager.getUserSettings()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notesFlow = combine(searchQuery, preferencesFlow) { query, preferences ->
        Pair(query, preferences)
    }.flatMapLatest { (searchQuery, preferencesFlow) ->
        getAllNotesUseCase(searchQuery, preferencesFlow.sortType)
    }

    val notes = notesFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onEvent(noteListEvent: NoteListEvent) {
        when (noteListEvent) {
            is NoteListEvent.SearchNote -> searchQuery.value = noteListEvent.query
            is NoteListEvent.SortNote -> {
                viewModelScope.launch {
                    preferencesManager.saveSortType(noteListEvent.sortType)
                }
            }
        }
    }


}
