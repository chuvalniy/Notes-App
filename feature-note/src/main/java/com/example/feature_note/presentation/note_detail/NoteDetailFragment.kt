package com.example.feature_note.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteDetailBinding
import com.example.feature_note.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel: NoteDetailViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNoteInfoWithUi()

        // to add edit
        binding.btnEdit.setOnClickListener {
            val action = NoteDetailFragmentDirections.actionDetailToAddEdit(viewModel.note)
            findNavController().navigate(action)
        }
        // delete
        binding.btnDelete.setOnClickListener {
            viewModel.onEvent(NoteDetailEvent.DeleteNote)
            findNavController().navigate(R.id.action_detail_to_list)
        }
        // go back
        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupNoteInfoWithUi() {
        binding.apply {
            tvTitle.text = viewModel.noteTitle
            tvContent.text = viewModel.noteContent
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteDetailBinding.inflate(inflater, container, false)

}