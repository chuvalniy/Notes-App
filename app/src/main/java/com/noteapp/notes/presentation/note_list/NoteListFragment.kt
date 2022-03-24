package com.noteapp.notes.presentation.note_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.noteapp.notes.core.BaseFragment
import com.noteapp.notes.R
import com.noteapp.notes.databinding.FragmentNoteListBinding
import com.noteapp.notes.core.utils.SortType
import com.noteapp.notes.core.utils.onQueryTextChanged
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
                    .actionListToDetail(note.id ?: 0)
                findNavController().navigate(action)
            }
        )

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                adapter.submitList(it)
            }
        }

        observeSearchViewState()

        binding.apply {
            rvNoteList.adapter = adapter
            fabAddNote.setOnClickListener {
                findNavController().navigate(R.id.action_noteListFragment_to_noteAddFragment)
            }
        }

        popupMenu()
    }

    private fun popupMenu() {
        val popupMenu = PopupMenu(activity, binding.icSort)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newest -> {
                    viewModel.sortType.value = SortType.DESCENDING
                    true
                }
                R.id.oldest -> {
                    viewModel.sortType.value = SortType.ASCENDING
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

    private fun observeSearchViewState() {
        binding.searchNote.apply {
            onQueryTextChanged { query ->
                viewModel.queryText.value = query
            }
        }
    }


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoteListBinding.inflate(inflater, container, false)

}