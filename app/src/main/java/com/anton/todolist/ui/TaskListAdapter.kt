package com.anton.todolist.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anton.todolist.data.model.Task
import com.anton.todolist.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SimpleTimeZone


class TaskListAdapter(
    private var onEdit: (Task) -> Unit,
    private var onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskListAdapter.TaskListViewHolder>(TaskDiffCallback()) {

    class TaskListViewHolder(
        private val binding: ListItemBinding,
        private val onEdit: (Task) -> Unit,
        private val onDelete: (Task) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameView = binding.name
        private val description = binding.description
        private val alarmTime = binding.alarmTime
        private val alarmDate = binding.alarmDate

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(task: Task) {
            nameView.text = task.name
            description.text = task.description

            if (task.alarmTime != null) {
                alarmTime.text = task.alarmTime.timestampToLocalTime().toString()
                alarmTime.visibility = View.VISIBLE
            } else {
                alarmTime.visibility = View.GONE
            }

            if (task.alarmDate != null) {
                alarmDate.text = task.alarmDate.timestampToLocalDate().toString()
                alarmDate.visibility = View.VISIBLE
            } else {
                alarmDate.visibility = View.GONE
            }

            binding.deleteButton.setOnClickListener {
                onDelete(task)
            }
            binding.root.setOnClickListener {
                onEdit(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskListViewHolder(
        ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onEdit,
        onDelete
    )


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
