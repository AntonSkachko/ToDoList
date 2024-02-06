package com.anton.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class EntryTimeViewModel() : ViewModel() {
    private val _selectedTime = MutableLiveData<LocalTime?>()
    private val _selectedDate = MutableLiveData<LocalDate?>()

    val selectedTime: LiveData<LocalTime?> = _selectedTime
    val selectedDate: LiveData<LocalDate?> = _selectedDate

    fun setTime(time: LocalTime?) {
        _selectedTime.value = time
    }

    fun setDate(date: LocalDate?) {
        _selectedDate.value = date
    }
}