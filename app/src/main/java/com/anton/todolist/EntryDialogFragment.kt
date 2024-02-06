package com.anton.todolist

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anton.todolist.databinding.FragmentEntryDialogBinding
import com.anton.todolist.ui.AppViewModelProvider
import com.anton.todolist.ui.EntryTimeViewModel
import com.anton.todolist.ui.EntryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class EntryDialogFragment : BottomSheetDialogFragment() {

    private val entryViewModel by viewModels<EntryViewModel> {
        AppViewModelProvider.Factory
    }

    private val entryTimeViewModel by activityViewModels<EntryTimeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEntryDialogBinding
            .inflate(inflater, container, false).root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val binding = FragmentEntryDialogBinding.bind(view)
        val args: EntryDialogFragmentArgs by navArgs()
        val taskId = args.id



        binding.setTime.setOnClickListener {
            navController.navigate(
                EntryDialogFragmentDirections.actionEntryDialogFragmentToTimePickerFragment()
            )
        }

        binding.setDate.setOnClickListener {
            navController.navigate(
                EntryDialogFragmentDirections.actionEntryDialogFragmentToDatePickerFragment()
            )
        }

        if (args.id > 0) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    entryViewModel.getTaskStream(args.id)
                        .filterNotNull().collect { item ->
                            with(binding) {
                                name.setText(item.name)
                                description.setText(item.description)
                            }
                        }
                }
            }
        }

        binding.name.doOnTextChanged { _, start, _, count ->
            binding.saveButton.isEnabled = (start + count) > 0
        }

        binding.saveButton.setOnClickListener {

            entryViewModel.saveTask(
                taskId,
                binding.name.text.toString(),
                binding.description.text.toString(),
                entryTimeViewModel.selectedTime.value,
                entryTimeViewModel.selectedDate.value
            )
            entryTimeViewModel.setTime(null)
            entryTimeViewModel.setDate(null)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

    }
}