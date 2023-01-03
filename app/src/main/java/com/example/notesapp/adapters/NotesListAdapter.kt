package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NotesListItemBinding
import com.example.notesapp.model.Note

class NotesListAdapter(private val clickListener: NotesItemListener):
    ListAdapter<Note, NotesListAdapter.NotesViewHolder>(DiffCallback){

    class NotesViewHolder(private var binding: NotesListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: NotesItemListener, note: Note){
            binding.clickListener = clickListener
            binding.note = note
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesViewHolder(
            NotesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    object DiffCallback: DiffUtil.ItemCallback<Note>(){

        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem.title == newItem.title
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.bind(clickListener, getItem(position))


    class NotesItemListener(val clickListener: (note: Note) -> Unit){
        fun onClick(note: Note) = clickListener(note)
    }
}