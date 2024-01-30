package com.anton.todolist.data.repository

import com.anton.todolist.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val taskStream: Flow<List<Task>>

    fun getTaskStream(id: Long): Flow<Task?>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
}