package com.anton.todolist.container

import android.content.Context
import com.anton.todolist.data.AppDatabase
import com.anton.todolist.data.repository.RoomTaskRepository
import com.anton.todolist.data.repository.TaskRepository

class AppDataContainer(
    override val context: Context
) : AppContainer {

    override val taskRepository: TaskRepository by lazy {
        RoomTaskRepository(AppDatabase.getDatabase(context).taskDao())
    }
}