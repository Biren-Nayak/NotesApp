package com.example.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapters.NotesListAdapter
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deselectNote()

        NotesListAdapter (
            NotesListAdapter.NotesItemListener{ note ->
                viewModel.onNoteClicked(note)
                navigator()
            }
        ).apply {
            binding.recyclerView.adapter = this
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
            viewModel.notesList.observe(viewLifecycleOwner) {
                submitList(it)
            }
        }

        binding.addNoteButton.setOnClickListener { navigator() }

    }

    private fun navigator() = findNavController().navigate(R.id.action_mainFragment_to_addNotesFragment)

}
