package com.ae.aetask.ui.model

import com.ae.aetask.database.table.TaskTable

class TaskModel(tasks: List<TaskTable>) {
    private var tasksList = tasks

    fun getTasksList(): List<TaskTable> {
        return tasksList
    }
}