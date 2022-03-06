package com.example.notes.presentation.note_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.BaseFragment
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private lateinit var adapter: NoteListAdapter

    private val viewModel: NoteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteListAdapter(
            onMoveToDetail = { note ->
                val action = NoteListFragmentDirections
                    .actionListToDetail(note.id!!)
                findNavController().navigate(action)
            }
        )

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            rvNoteList.adapter = adapter
            fabAddNote.setOnClickListener {
                findNavController().navigate(R.id.action_noteListFragment_to_noteAddFragment)
            }
            searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchDatabase(query)
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (query != null) {
                        searchDatabase(query)
                    }
                    return true
                }
            })
        }

        popupMenu()
    }

    private fun popupMenu() {
        val popupMenu = PopupMenu(activity, binding.icSort)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newest -> {
                    viewModel.notesByDescending.observe(viewLifecycleOwner) { notesByDesc ->
                        notesByDesc?.let {
                            adapter.submitList(it)
                        }
                    }
                    true
                }
                R.id.oldest -> {
                    viewModel.notesByAscending.observe(viewLifecycleOwner) { notesByAsc ->
                        notesByAsc?.let {
                            adapter.submitList(it)
                        }
                    }
                    true
                }
                else -> true
            }
        }

        binding.icSort.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
        }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this.viewLifecycleOwner) { notes ->
            notes?.let {
                adapter.submitList(it)
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container, false)

}