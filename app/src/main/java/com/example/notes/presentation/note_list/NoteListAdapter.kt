package com.example.notes.presentation.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.AdapterNoteItemBinding
import com.example.notes.domain.model.Note
import java.text.SimpleDateFormat

class NoteListAdapter(
    private var onDelete: (Note) -> Unit,
    private var onMoveToDetail: (Note) -> Unit
) : ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback) {

    class NoteViewHolder(
        private val binding: AdapterNoteItemBinding,
        private var onDelete: (Note) -> Unit,
        private var onMoveToDetail: (Note) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private fun getDateString(time: Long): String {
            val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy")

            return simpleDateFormat.format(time * 1000L)
        }

        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvDate.text = getDateString(note.timestamp)
                cvNoteItem.setOnClickListener {
                    onMoveToDetail(note)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(
            AdapterNoteItemBinding.inflate(layoutInflater, parent, false),
            onDelete,
            onMoveToDetail
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == oldItem.id
        }

    }
}