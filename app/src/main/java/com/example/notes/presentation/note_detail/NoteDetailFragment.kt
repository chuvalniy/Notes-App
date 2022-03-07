package com.example.notes.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.BaseFragment
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteDetailBinding
import com.example.notes.domain.model.Note
import com.example.notes.utils.getDateString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val navigationArgs: NoteDetailFragmentArgs by navArgs()

    private val viewModel: NoteDetailViewModel by viewModels()

    private lateinit var note: Note

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id

        viewModel.getOneNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
            note = selectedNote
            binding.apply {
                tvTitle.text = note.title
                tvContent.text = note.content
                btnDelete.setOnClickListener {
                    viewModel.deleteNote(note)
                    findNavController().navigate(R.id.action_detail_to_list)
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val action = NoteDetailFragmentDirections
                .actionDetailToAddEdit(note.id!!)
            findNavController().navigate(action)
        }
        binding.btnGoBack.setOnClickListener {
            findNavController().navigate(R.id.action_detail_to_list)
        }
    }

}