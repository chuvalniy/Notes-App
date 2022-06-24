package com.example.feature_note.presentation.note_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.common.ui.BaseFragment
import com.example.common.ui.onQueryTextChanged
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private var adapter: NoteListAdapter? = null
    private val viewModel: NoteListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        observeData()
        handleSearch()

        navigateToNoteAddFragment()
        navigateToSortDialogFragment(savedInstanceState)
    }

    private fun navigateToSortDialogFragment(savedInstanceState: Bundle?) {
        binding.icSort.setOnClickListener {
            if (savedInstanceState == null) {
                NoteListBottomSheetFragment().show(
                    parentFragmentManager,
                    NoteListBottomSheetFragment.SORT_BOTTOM_SHEET_FRAGMENT_TAG
                )
            }
        }
    }

    private fun navigateToNoteAddFragment() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_noteAddFragment)
        }
    }

    private fun handleSearch() {
        binding.searchNote.onQueryTextChanged { query ->
            viewModel.onEvent(NoteListEvent.SearchNote(query))
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notes.collect { notes ->
                adapter?.submitList(notes)
            }
        }
    }

    private fun setupAdapter() {
        adapter = NoteListAdapter(
            onMoveToDetail = { note ->
                val action = NoteListFragmentDirections
                    .actionListToDetail(note.id ?: 0)
                findNavController().navigate(action)
            }
        )

        binding.rvNoteList.adapter = adapter
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container, false)

}