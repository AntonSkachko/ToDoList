package com.anton.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anton.todolist.data.model.Task
import com.anton.todolist.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.Date


class TaskListAdapter(
    private var onEdit: (Task) -> Unit,
    private var onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskListAdapter.JuiceListViewHolder>(JuiceDiffCallback()) {

    class JuiceListViewHolder(
        private val binding: ListItemBinding,
        private val onEdit: (Task) -> Unit,
        private val onDelete: (Task) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameView = binding.name
        private val description = binding.description
        private val time = binding.time

        fun bind(task: Task) {
            nameView.text = task.name
            description.text = task.description
            time.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(task.date)).toString()

            binding.deleteButton.setOnClickListener {
                onDelete(task)
            }
            binding.root.setOnClickListener {
                onEdit(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JuiceListViewHolder(
        ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onEdit,
        onDelete
    )

    override fun onBindViewHolder(holder: JuiceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class JuiceDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
