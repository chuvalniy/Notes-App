package com.example.notes.presentation.note_add

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.BaseFragment
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteAddBinding
import com.example.notes.domain.model.Note
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
                binding.apply {
                    edTitle.setText(selectedNote.title)
                    edContent.setText(selectedNote.content)
                    btnAddNote.setOnClickListener {
                        updateNote()
                    }
                }
            }
        }

        binding.btnAddNote.setOnClickListener {
            insertNote()
        }
    }


    private fun updateNote() {
        viewModel.updateNote(
            Note(
                id = navigationArgs.id,
                title = binding.edTitle.text.toString(),
                content = binding.edContent.text.toString(),
                color = 123,
                timestamp = System.currentTimeMillis() / 1000L
            )
        )
        findNavController().navigate(
            R.id.action_add_edit_to_list
        )
    }

    private fun insertNote() {
        viewModel.insertNote(
            Note(
                title = binding.edTitle.text.toString(),
                content = binding.edContent.text.toString(),
                color = 123,
                timestamp = System.currentTimeMillis() / 1000L
            )
        )
        findNavController().navigate(R.id.action_add_edit_to_list)
    }


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)
}