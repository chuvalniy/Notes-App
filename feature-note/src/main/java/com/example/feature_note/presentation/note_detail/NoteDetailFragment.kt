package com.example.feature_note.presentation.note_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteDetailBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel by stateViewModel<NoteDetailViewModel>(state = {
        val bundle = Bundle()
        bundle.putParcelable("note", arguments?.getParcelable("note"))
        bundle
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNoteInfoToUi()

        observeUiEvents()

        handleButtonClicks()
    }

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteDetailsEvent.collect { event ->
                when (event) {
                    is NoteDetailViewModel.UiNoteDetailsEvent.NavigateToAddEditScreen -> {
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

    private fun setNoteInfoToUi() {
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