package com.anton.todolist.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anton.todolist.data.model.Task
import com.anton.todolist.data.repository.TaskRepository
import com.anton.todolist.service.AlarmScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.TemporalField
import kotlin.math.log

class EntryViewModel(
    private val taskRepository: TaskRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    fun getTaskStream(id: Long): Flow<Task?> = taskRepository.getTaskStream(id)

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTask(
        id: Long,
        name: String,
        description: String,
        selectedTime: LocalTime?,
        selectedDate: LocalDate?
    ) {
        val task = Task(
            id, name, description,
            selectedTime?.localTimeToTimestamp(),
            selectedDate?.localDateToTimestamp()
        )
        viewModelScope.launch {
            if (id > 0) {
                alarmScheduler.cancel(task)
                taskRepository.updateTask(task)
            } else {
                taskRepository.addTask(task)
            }
            alarmScheduler.schedule(task)
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.localDateToTimestamp(): Long {
    return this
        .atStartOfDay()
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.timestampToLocalDate(): LocalDate {
    return LocalDateTime
        .ofEpochSecond(this / 1000, 0, ZoneOffset.UTC)
        .toLocalDate()
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.localTimeToTimestamp(): Long {
    return this
        .atDate(LocalDate.ofEpochDay(0))
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
}


@RequiresApi(Build.VERSION_CODES.O)
fun Long.timestampToLocalTime(): LocalTime {
    return LocalDateTime
        .ofEpochSecond( this / 1000, 0, ZoneOffset.UTC)
        .toLocalTime()
}

