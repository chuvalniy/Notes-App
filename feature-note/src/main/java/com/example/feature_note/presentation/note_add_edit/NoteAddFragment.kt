package com.example.feature_note.presentation.note_add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteAddBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel: NoteAddViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiEvent()

        binding.apply {
            edTitle.setText(viewModel.noteTitle)
            edContent.setText(viewModel.noteContent)

            edTitle.addTextChangedListener {
                viewModel.onEvent(NoteAddEditEvent.TitleChanged(it.toString()))
            }
            edContent.addTextChangedListener {
                viewModel.onEvent(NoteAddEditEvent.ContentChanged(it.toString()))
            }
        }

        handleButtonClicks()
    }

    private fun handleButtonClicks() = binding.apply {
        btnSaveNote.setOnClickListener {
            viewModel.onEvent(NoteAddEditEvent.NoteSubmitted)
        }
        btnGoBack.setOnClickListener {
            viewModel.onEvent(NoteAddEditEvent.BackButtonClicked)
        }
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteAddEditEvent.collect { event ->
                when (event) {
                    is NoteAddViewModel.UiAddEditEvent.ShowSnackbar -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }
                    is NoteAddViewModel.UiAddEditEvent.NavigateToListScreen -> {
                        findNavController().navigate(R.id.action_add_edit_to_list)
                    }
                    is NoteAddViewModel.UiAddEditEvent.NavigateBack -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)

}