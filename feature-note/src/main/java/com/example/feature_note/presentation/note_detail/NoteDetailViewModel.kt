package com.example.feature_note.presentation.note_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.feature_note.domain.model.Note
import com.example.feature_note.domain.repository.NoteRepository
import com.example.feature_note.domain.use_case.DeleteNoteUseCase
import com.example.feature_note.domain.use_case.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getOneNoteUseCase: GetNoteUseCase
) : ViewModel() {


    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun getOneNote(id: Int): LiveData<Note> {
        return getOneNoteUseCase(id).asLiveData()
    }
}