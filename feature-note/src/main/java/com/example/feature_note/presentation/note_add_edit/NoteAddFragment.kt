package com.example.feature_note.presentation.note_add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteAddBinding
import com.example.feature_note.presentation.note_detail.NoteDetailFragmentArgs
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel by stateViewModel<NoteAddViewModel>(state = {
        val bundle = Bundle()
        bundle.putParcelable("note", arguments?.getParcelable("note"))
        bundle
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setNoteInfoToUi()

        observeUiEvent()

        listenToNoteChanges()
        handleButtonClicks()
    }

    private fun listenToNoteChanges() {
        binding.edTitle.addTextChangedListener {
            viewModel.onEvent(NoteAddEditEvent.TitleChanged(it.toString()))
        }
        binding.edContent.addTextChangedListener {
            viewModel.onEvent(NoteAddEditEvent.ContentChanged(it.toString()))
        }
    }

    private fun setNoteInfoToUi() {
        binding.edTitle.setText(viewModel.noteTitle)
        binding.edContent.setText(viewModel.noteContent)
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