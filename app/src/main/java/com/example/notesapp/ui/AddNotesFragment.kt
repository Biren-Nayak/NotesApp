package com.example.notesapp.ui

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddNotesBinding
import com.example.notesapp.model.Note
import com.example.notesapp.utils.Constants.Crud
import com.example.notesapp.utils.Constants.Crud.*
import com.example.notesapp.viewmodels.MainViewModel


class AddNotesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAddNotesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(viewModel.selectedNote.value != null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddNotesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.doneButton.setOnClickListener {
            checkAndAddNote()
        }

        binding.contentText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE){
                checkAndAddNote()
                return@setOnEditorActionListener true
            }
            false
        }

        viewModel.selectedNote.observe(viewLifecycleOwner){
            it?.let { note ->
                binding.titleText.setText(note.title)
                binding.contentText.setText(note.data)
            }
        }

    }

    private fun checkAndAddNote() {
        if (binding.titleText.text.toString().isEmpty()) {
            binding.titleText.error = "Please enter title"
            return
        }

        if (binding.contentText.text.toString().isEmpty()) {
            binding.nameLayout.error = "Please enter content"
            return
        }

        addNote()

    }

    private fun addNote() {
        val titleText = binding.titleText.text.toString()
        val contentText = binding.contentText.text.toString()
        val selectedNote = viewModel.selectedNote.value

        if (selectedNote != null) {
            viewModel.add( Note(titleText, contentText, selectedNote.id, selectedNote.firestoreId ) )
            showToastAndNavigate(titleText, Updated)
        }
        else {
            viewModel.add( Note( titleText, contentText ) )
            showToastAndNavigate(titleText, Created)
        }
    }



    @Deprecated("Deprecated in Java",
        ReplaceWith("menuInflater.inflate(R.menu.menu, menu)", "com.example.notesapp.R"))
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete)
            viewModel.selectedNote.value?.let { note ->
                showToastAndNavigate(note.title, Deleted)
                viewModel.delete(note)
            }
        return super.onContextItemSelected(item)
    }


    private fun showToastAndNavigate(text: String, crud: Crud) = findNavController().popBackStack().also {
        Toast.makeText(
            context,
            "$text ${crud.toString().lowercase()} successfully",
            Toast.LENGTH_SHORT
        ).show()
    }


}