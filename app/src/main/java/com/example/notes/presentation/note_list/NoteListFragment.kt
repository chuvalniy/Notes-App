package com.example.notes.presentation.note_list


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.BaseFragment
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.domain.util.SortType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private lateinit var adapter: NoteListAdapter

    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteListAdapter(
            onDelete = { note ->
                viewModel.deleteNote(note)
            },
            onMoveToDetail = { note ->
                val action = NoteListFragmentDirections
                    .actionListToDetail(note.id!!)
                findNavController().navigate(action)
            }
        )

        viewModel.notesState.observe(viewLifecycleOwner) { notes ->
            notes.let {
                adapter.submitList(it.notes)
            }
        }

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_noteAddFragment)
        }

        binding.rvNoteList.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortDescending -> {
                viewModel.getNotes(SortType.Descending)
            }
            R.id.sortAscending -> {
                viewModel.getNotes(SortType.Ascending)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container, false)

}