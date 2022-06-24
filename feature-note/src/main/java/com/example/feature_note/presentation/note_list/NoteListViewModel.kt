package com.example.feature_note.presentation.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.feature_note.data.local.settings.PreferencesManager
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.feature_note.domain.use_case.GetAllNotesUseCase
import com.example.feature_note.domain.use_case.InsertNoteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val preferencesManager: PreferencesManager,
    private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    
    val preferencesFlow = preferencesManager.getUserSettings()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notesFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, preferences ->
        Pair(query, preferences)
    }.flatMapLatest { (searchQuery, preferencesFlow) ->
        getAllNotesUseCase(searchQuery, preferencesFlow.sortType)
    }

    val notes = notesFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var recentlyDeletedNote: Note? = null

    fun onEvent(noteListEvent: NoteListEvent) {
        when (noteListEvent) {
            is NoteListEvent.SearchNote -> searchQuery.value = noteListEvent.query
            is NoteListEvent.SortNote -> {
                viewModelScope.launch {
                    preferencesManager.saveSortType(noteListEvent.sortType)
                }
            }
            is NoteListEvent.SwipeNote -> {
                viewModelScope.launch {
                    deleteNoteUseCase(noteListEvent.note)
                    recentlyDeletedNote = noteListEvent.note
                }
            }
            is NoteListEvent.RestoreDeletedNote -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let { recentlyDeletedNote ->
                        insertNoteUseCase(recentlyDeletedNote)
                    }
                    recentlyDeletedNote = null
                }
            }
        }
    }
}
