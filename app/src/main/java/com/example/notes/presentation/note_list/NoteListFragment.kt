package com.example.notes.presentation.note_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.notes.BaseFragment
import com.example.notes.databinding.FragmentNoteListBinding

class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private lateinit var adapter: NoteListAdapter

    private val viewModel: NoteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteListAdapter()

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notes.let {
                adapter.submitList(it)
            }
        }

        binding.rvNoteList.adapter = adapter

    }
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container,false)

}