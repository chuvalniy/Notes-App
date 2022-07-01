package com.example.feature_note.presentation.note_list

import androidx.lifecycle.*
import com.example.feature_note.data.local.settings.PreferencesManager
import com.example.feature_note.data.local.settings.SortType
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

    private val _noteListEventChannel = Channel<UiNoteListEvent>()
    val noteListEventFlow get() = _noteListEventChannel.receiveAsFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val notesFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, preferences ->
        Pair(query, preferences)
    }.flatMapLatest { (searchQuery, preferences) ->
        getAllNotesUseCase(searchQuery, preferences.sortType)
    }

    val notes = notesFlow.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.QueryEntered -> searchQuery.value = event.query
            is NoteListEvent.SortOrderSelected -> saveSortType(event.sortType)
            is NoteListEvent.NoteSwiped -> swipeToDeleteNote(event.note)
            is NoteListEvent.DeletedNoteRestored -> restoreDeletedNote(event.note)
            is NoteListEvent.AddNewNoteClicked -> navigateToAddEditScreen()
            is NoteListEvent.NoteSelected -> navigateToDetailsNoteScreen(event.note)
            is NoteListEvent.SortButtonClicked -> navigateToSortDialogScreen()
        }
    }

    private fun saveSortType(sortType: SortType) {
        viewModelScope.launch {
            preferencesManager.saveSortType(sortType)
        }
    }

    private fun swipeToDeleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
            _noteListEventChannel.send(UiNoteListEvent.ShowUndoDeleteNoteMessage(note))
        }
    }

    private fun restoreDeletedNote(note: Note) {
        viewModelScope.launch { insertNoteUseCase(note) }
    }

    private fun navigateToAddEditScreen() {
        viewModelScope.launch {
            _noteListEventChannel.send(UiNoteListEvent.NavigateToAddEditScreen)
        }
    }

    private fun navigateToSortDialogScreen() {
        viewModelScope.launch {
            _noteListEventChannel.send(UiNoteListEvent.NavigateToSortDialogScreen)
        }
    }

    private fun navigateToDetailsNoteScreen(note: Note) {
        viewModelScope.launch {
            _noteListEventChannel.send(UiNoteListEvent.NavigateToDetailsNoteScreen(note))
        }
    }

    sealed class UiNoteListEvent {
        data class ShowUndoDeleteNoteMessage(val note: Note) : UiNoteListEvent()
        data class NavigateToDetailsNoteScreen(val note: Note) : UiNoteListEvent()
        object NavigateToAddEditScreen : UiNoteListEvent()
        object NavigateToSortDialogScreen : UiNoteListEvent()
    }
}
