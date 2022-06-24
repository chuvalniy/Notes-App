package com.example.feature_note.presentation.note_add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteAddBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>() {

    private val viewModel: NoteAddViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            edTitle.setText(viewModel.noteTitle)
            edContent.setText(viewModel.noteContent)

            edTitle.addTextChangedListener {
                viewModel.noteTitle = it.toString()
            }
            edContent.addTextChangedListener {
                viewModel.noteContent = it.toString()
            }

            btnGoBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnSaveNote.setOnClickListener {
                viewModel.onEvent(NoteAddEvent.SaveNote)
                findNavController().navigate(R.id.action_add_edit_to_list)
            }
        }

    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteAddBinding.inflate(inflater, container, false)

}