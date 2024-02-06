package com.anton.todolist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDateTime

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val alarmTime: Long? = null,
    val alarmDate: Long? = null
//    val category: String
)