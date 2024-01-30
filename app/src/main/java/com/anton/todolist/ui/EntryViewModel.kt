package com.anton.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.todolist.data.model.Task
import com.anton.todolist.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar

class EntryViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    fun getTaskStream(id: Long): Flow<Task?> = taskRepository.getTaskStream(id)

    fun saveTask(
        id: Long,
        name: String,
        description: String
    ) {
        val task = Task(id, name, description, Calendar.getInstance().timeInMillis)
        viewModelScope.launch {
            if (id > 0) {
                taskRepository.updateTask(task)
            } else {
                taskRepository.addTask(task)
            }
        }
    }
}