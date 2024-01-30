package com.anton.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.anton.todolist.databinding.FragmentTodoBinding
import com.anton.todolist.ui.AppViewModelProvider
import com.anton.todolist.ui.TaskListAdapter
import com.anton.todolist.ui.TodoListViewModel
import kotlinx.coroutines.launch

class TodoListFragment: Fragment() {

    private val viewModel by viewModels<TodoListViewModel> {
        AppViewModelProvider.Factory
    }

    private val adapter = TaskListAdapter(
        onEdit = {task ->
            findNavController().navigate(
                TodoListFragmentDirections.actionTodoListFragment3ToEntryDialogFragment2(task.id)
            )
        },

        onDelete = {task ->
            viewModel.deleteTask(task)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTodoBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTodoBinding.bind(view)
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener { fabView ->
            fabView.findNavController().navigate(
                TodoListFragmentDirections.actionTodoListFragment3ToEntryDialogFragment2()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taskStream.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}