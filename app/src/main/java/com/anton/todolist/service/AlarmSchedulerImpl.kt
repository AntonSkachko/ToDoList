package com.anton.todolist.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import com.anton.todolist.data.model.Task

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun schedule(task: Task) {


        Log.d("Context", "context in alarmScheduler $context")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (alarmManager.canScheduleExactAlarms()) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("taskId", task.id.toString())
                putExtra("taskName", task.name)
                putExtra("taskDescription", task.description)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notificationTime = task.alarmDate!! + task.alarmTime!! - System.currentTimeMillis()

            alarmManager.setExact(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                notificationTime + SystemClock.elapsedRealtime(),
                pendingIntent
            )
        }

    }

    override fun cancel(task: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, task.id.toInt(), alarmIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
}