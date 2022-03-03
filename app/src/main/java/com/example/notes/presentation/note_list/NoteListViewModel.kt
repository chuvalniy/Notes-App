package com.example.notes.presentation.note_list

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

    private val _notesState = MutableLiveData<NoteListState>()
    val notesState: LiveData<NoteListState> = _notesState

    init {
        getNotes(sortType = SortType.Ascending)
    }

    fun getNotes(sortType: SortType) {
        repository.getAllNotes(sortType)
            .onEach { notes ->
                _notesState.value = NoteListState(
                    notes = notes,
                    sortType = sortType
                )
            }
            .launchIn(viewModelScope)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}