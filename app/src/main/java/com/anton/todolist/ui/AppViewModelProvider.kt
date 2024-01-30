package com.anton.todolist.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.todolist.ToDoListApplication


object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            EntryViewModel(toDoListApplication().container.taskRepository)
        }

        initializer {
            TodoListViewModel(toDoListApplication().container.taskRepository)
        }
    }
}

fun CreationExtras.toDoListApplication(): ToDoListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as
            ToDoListApplication)