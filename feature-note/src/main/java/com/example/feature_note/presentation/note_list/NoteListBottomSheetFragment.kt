package com.example.feature_note.presentation.note_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteListBottomSheetBinding
import com.example.feature_note.utils.SortType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteListBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNoteListBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handleSortEvent()
    }

    private fun handleSortEvent() {
        binding.apply {
            rbSortAscending.setOnClickListener {
                viewModel.onEvent(NoteListEvent.SortNote(SortType.ASCENDING))
            }
            rbSortDescending.setOnClickListener {
                viewModel.onEvent(NoteListEvent.SortNote(SortType.DESCENDING))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SORT_BOTTOM_SHEET_FRAGMENT_TAG = "SORT_BOTTOM_SHEET_FRAGMENT_TAG"
    }
}