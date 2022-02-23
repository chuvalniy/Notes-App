package com.example.notes.presentation.note_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.BaseFragment
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteAddBinding
import com.example.notes.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel: NoteAddViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNote.setOnClickListener {
            insertNote()
        }
    }

    private fun insertNote() {
        viewModel.insertNote(
            Note(
                title = binding.edTitle.text.toString(),
                content = binding.edContent.text.toString(),
                color = 123,
                timestamp = 123,
                isImportant = false
            )
        )
        findNavController().navigate(R.id.action_add_edit_to_list)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)
}