package com.anton.todolist.container

import android.content.Context
import com.anton.todolist.data.repository.TaskRepository

interface AppContainer {
    val taskRepository: TaskRepository
    val context: Context
}