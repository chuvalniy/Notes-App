package com.example.feature_note.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel: NoteDetailViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNoteInfoWithUi()

        observeUiEvents()

        handleButtonClicks()
    }

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteDetailsEvent.collect { event ->
                when(event) {
                    is NoteDetailViewModel.UiNoteDetailsEvent.NavigateToDetailsScreen -> {
                        val action = NoteDetailFragmentDirections.actionDetailToAddEdit(event.note)
                        findNavController().navigate(action)
                    }
                    is NoteDetailViewModel.UiNoteDetailsEvent.NavigateToListScreen -> {
                        findNavController().navigate(R.id.action_detail_to_list)
                    }
                    is NoteDetailViewModel.UiNoteDetailsEvent.NavigateBack -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun handleButtonClicks() = binding.apply {
        btnEdit.setOnClickListener {
            viewModel.onEvent(NoteDetailEvent.EditButtonClicked)
        }
        btnDelete.setOnClickListener {
            viewModel.onEvent(NoteDetailEvent.NoteDeleted)
        }
        btnGoBack.setOnClickListener {
            viewModel.onEvent(NoteDetailEvent.BackButtonClicked)
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