package com.noteapp.notes.presentation.note_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.noteapp.notes.core.BaseFragment
import com.noteapp.notes.R
import com.noteapp.notes.databinding.FragmentNoteAddBinding
import com.noteapp.notes.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel: NoteAddViewModel by viewModels()

    private val navigationArgs: NoteAddFragmentArgs by navArgs()

    private lateinit var note: Note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getOneNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
                note = selectedNote
                bindUpdate(note)
            }
        } else {
            binding.btnAddNote.setOnClickListener {
                insertNote()
            }
        }
        binding.btnGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_add_edit_to_list)
        }
    }

    private fun bindUpdate(note: Note) {
        binding.apply {
            edTitle.setText(note.title)
            edContent.setText(note.content)
            btnAddNote.setOnClickListener {
                updateNote(note)
            }
        }
    }

    private fun updateNote(note: Note) {
        viewModel.updateNote(
            Note(
                id = navigationArgs.id,
                title = binding.edTitle.text.toString(),
                content = binding.edContent.text.toString(),
                color = note.color,
            )
        )
        findNavController().navigate(R.id.action_add_edit_to_list)
    }

    private fun insertNote() {
        viewModel.insertNote(
            Note(
                title = binding.edTitle.text.toString(),
                content = binding.edContent.text.toString(),
                color = ContextCompat.getColor(
                    requireActivity(),
                    Note.colors.random()
                )
            )
        )
        findNavController().navigate(R.id.action_add_edit_to_list)
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)

}