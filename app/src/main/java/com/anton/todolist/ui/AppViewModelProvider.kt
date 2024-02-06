package com.anton.todolist.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.anton.todolist.ToDoListApplication
import com.anton.todolist.service.AlarmSchedulerImpl


object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            Log.d("Context", "context in initializer ${toDoListApplication().container.context}")
            EntryViewModel(
                toDoListApplication().container.taskRepository,
                AlarmSchedulerImpl(toDoListApplication().container.context)
            )
        }

        initializer {
            TodoListViewModel(toDoListApplication().container.taskRepository)
        }

        initializer {
            EntryTimeViewModel()
        }
    }
}

fun CreationExtras.toDoListApplication(): ToDoListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as
            ToDoListApplication)