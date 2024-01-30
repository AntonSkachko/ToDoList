package com.anton.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.todolist.data.model.Task
import com.anton.todolist.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    val taskStream: Flow<List<Task>> = taskRepository.taskStream

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }
}