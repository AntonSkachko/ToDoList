package com.anton.todolist.container

import com.anton.todolist.data.repository.TaskRepository

interface AppContainer {
    val taskRepository: TaskRepository
}