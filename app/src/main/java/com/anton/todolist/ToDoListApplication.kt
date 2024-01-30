package com.anton.todolist

import android.app.Application
import com.anton.todolist.container.AppContainer
import com.anton.todolist.container.AppDataContainer

class ToDoListApplication: Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this.applicationContext)
    }
}