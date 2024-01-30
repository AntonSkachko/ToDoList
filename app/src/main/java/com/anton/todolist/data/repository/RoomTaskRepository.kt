package com.anton.todolist.data.repository

import com.anton.todolist.data.dao.TaskDao
import com.anton.todolist.data.model.Task
import kotlinx.coroutines.flow.Flow

class RoomTaskRepository(
    private val taskDao: TaskDao
): TaskRepository {

    override val taskStream: Flow<List<Task>>
        get() = taskDao.getAll()

    override fun getTaskStream(id: Long): Flow<Task?> {
        return taskDao.get(id)
    }

    override suspend fun addTask(task: Task) {
        taskDao.insert(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }
}