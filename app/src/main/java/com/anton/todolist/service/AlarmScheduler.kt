package com.anton.todolist.service

import com.anton.todolist.data.model.Task

interface AlarmScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)
}