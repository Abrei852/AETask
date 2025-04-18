package com.ae.aetask.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.ae.aetask.database.table.TaskTable
import com.ae.aetask.databinding.FragmentTaskBinding
import com.ae.aetask.ui.interfaces.TaskInterface
import com.google.android.material.button.MaterialButton

class TaskRecyclerViewAdapter(
    private val taskList: List<TaskTable>,
    private val taskInterface: TaskInterface
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskList[position].let {
            holder.body.text = it.taskDbBody
            holder.taskDone.setOnClickListener {
                taskInterface.markTaskDone(taskList[position])
            }
        }
    }

    override fun getItemCount(): Int = taskList.size

    inner class ViewHolder(binding: FragmentTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val body: TextView = binding.tvTaskBody
        val taskDone: MaterialButton = binding.btnDone

        override fun toString(): String {
            return super.toString() + " '" + body.text + "'"
        }
    }

}