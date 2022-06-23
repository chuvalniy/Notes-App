package com.example.feature_note.presentation.note_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteAddBinding
import com.example.feature_note.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel: NoteAddViewModel by viewModels()

    private val navigationArgs: NoteAddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleNavigationArgs()

        binding.btnGoBack.setOnClickListener {
            navigateToNoteList()
        }
    }

    private fun handleNavigationArgs() {
        val id = navigationArgs.id
        if (id > 0) {
            observeUi(id)
        } else {
            binding.btnAddNote.setOnClickListener {
                insertNote()
            }
        }
    }

    private fun observeUi(id: Int) {
        viewModel.getOneNote(id).observe(viewLifecycleOwner) { selectedNote ->
            applyNotePropertiesToUi(selectedNote)
        }
    }

    private fun applyNotePropertiesToUi(note: Note) = binding.apply {
        edTitle.setText(note.title)
        edContent.setText(note.content)
        btnAddNote.setOnClickListener {
            updateNote(note)
        }
    }

    private fun updateNote(note: Note) {
        viewModel.onEvent(
            NoteAddEvent.UpdateNote(
                Note(
                    id = navigationArgs.id,
                    title = binding.edTitle.text.toString(),
                    content = binding.edContent.text.toString(),
                    color = note.color,
                )
            )
        )

        navigateToNoteList()
    }

    private fun insertNote() {
        viewModel.onEvent(
            NoteAddEvent.InsertNote(
                Note(
                    title = binding.edTitle.text.toString(),
                    content = binding.edContent.text.toString(),
                    color = ContextCompat.getColor(
                        requireActivity(),
                        Note.colors.random()
                    )
                )
            )
        )

        navigateToNoteList()
    }

    private fun navigateToNoteList() {
        findNavController().navigate(R.id.action_add_edit_to_list)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)

}