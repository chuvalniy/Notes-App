package com.example.feature_note.presentation.note_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.common.ui.BaseFragment
import com.example.common.ui.onQueryTextChanged
import com.example.feature_note.R
import com.example.feature_note.databinding.FragmentNoteListBinding
import com.example.feature_note.domain.model.Note
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private var adapter: NoteListAdapter? = null
    private val viewModel by sharedStateViewModel<NoteListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        observeData()
        observeUiEvent()

        handleSearch()
        handleButtonClicks()
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteListEventFlow.collect { event ->
                when (event) {
                    is NoteListViewModel.UiNoteListEvent.ShowUndoDeleteNoteMessage -> {
                        showUndoDeleteNoteMessage(event.note)
                    }
                    is NoteListViewModel.UiNoteListEvent.NavigateToAddEditScreen -> {
                        val action =
                            NoteListFragmentDirections.actionListToAddEdit()
                        findNavController().navigate(action)
                    }
                    is NoteListViewModel.UiNoteListEvent.NavigateToDetailsNoteScreen -> {
                        val action = NoteListFragmentDirections.actionListToDetail(event.note)
                        findNavController().navigate(action)
                    }
                    is NoteListViewModel.UiNoteListEvent.NavigateToSortDialogScreen -> {
                        findNavController().navigate(R.id.action_list_to_dialog_sort)
                    }
                    is NoteListViewModel.UiNoteListEvent.ShowSnackbar -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun handleButtonClicks() {
        binding.fabAddNote.setOnClickListener {
            viewModel.onEvent(NoteListEvent.AddNewNoteClicked)
        }
        binding.icSort.setOnClickListener {
            viewModel.onEvent(NoteListEvent.SortButtonClicked)
        }
    }

    private fun showUndoDeleteNoteMessage(note: Note) {
        Snackbar.make(requireView(), "Note deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                viewModel.onEvent(NoteListEvent.DeletedNoteRestored(note))
            }
            .show()
    }

    private fun handleSearch() {
        binding.searchNote.onQueryTextChanged { query ->
            viewModel.onEvent(NoteListEvent.QueryEntered(query))
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
                viewModel.onEvent(NoteListEvent.NoteSelected(note))
            }
        )
        binding.rvNoteList.adapter = adapter

        setupItemTouchHelper()
    }

    private fun setupItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter?.currentList?.get(viewHolder.adapterPosition)
                note?.let { viewModel.onEvent(NoteListEvent.NoteSwiped(note)) }
            }
        }).attachToRecyclerView(binding.rvNoteList)
    }


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container, false)

}