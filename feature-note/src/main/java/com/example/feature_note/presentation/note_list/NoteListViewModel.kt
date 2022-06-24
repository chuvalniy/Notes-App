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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle
) : ViewModel() {

    private val searchQuery = state.getLiveData("searchQuery", "")

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

    private val _noteListEventChannel = Channel<UiNoteListEvent>()
    val noteListEventFlow get() = _noteListEventChannel.receiveAsFlow()


    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.QueryEntered -> searchQuery.value = event.query
            is NoteListEvent.SortOrderSelected -> {
                viewModelScope.launch { preferencesManager.saveSortType(event.sortType) }
            }
            is NoteListEvent.NoteSwiped -> {
                viewModelScope.launch {
                    deleteNoteUseCase(event.note)
                    _noteListEventChannel.send(UiNoteListEvent.ShowUndoDeleteNoteMessage(event.note))
                }
            }
            is NoteListEvent.DeletedNoteRestored -> {
                viewModelScope.launch { insertNoteUseCase(event.note) }
            }
            is NoteListEvent.AddNewNoteClicked -> {
                viewModelScope.launch { _noteListEventChannel.send(UiNoteListEvent.NavigateToAddNoteScreen) }
            }
            is NoteListEvent.NoteSelected -> {
                viewModelScope.launch {
                    _noteListEventChannel.send(UiNoteListEvent.NavigateToDetailsNoteScreen(event.note))
                }
            }
            is NoteListEvent.SortButtonClicked -> {
                viewModelScope.launch {
                    _noteListEventChannel.send(UiNoteListEvent.NavigateToSortDialogScreen)
                }
            }
        }
    }

    sealed class UiNoteListEvent {
        data class ShowUndoDeleteNoteMessage(val note: Note) : UiNoteListEvent()
        data class NavigateToDetailsNoteScreen(val note: Note) : UiNoteListEvent()
        object NavigateToAddNoteScreen : UiNoteListEvent()
        object NavigateToSortDialogScreen : UiNoteListEvent()
    }
}
