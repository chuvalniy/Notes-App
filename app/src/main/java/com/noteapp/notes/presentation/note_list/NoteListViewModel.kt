package com.noteapp.notes.presentation.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.noteapp.notes.domain.repository.NoteRepository
import com.noteapp.notes.core.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val sortType = MutableStateFlow(SortType.ASCENDING)
    val queryText = MutableStateFlow("")

    private val sortFlow = combine(
        sortType,
        queryText
    ) { sortType, queryText ->
        Pair(sortType, queryText)
    }.flatMapLatest { (sortType, queryText) ->
        repository.getAllNotes(sortType, queryText)
    }

    val notes = sortFlow.asLiveData()
}