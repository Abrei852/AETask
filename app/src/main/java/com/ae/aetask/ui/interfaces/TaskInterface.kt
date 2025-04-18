package com.ae.aetask.ui.interfaces

import com.ae.aetask.database.table.TaskTable

interface TaskInterface {
    fun markTaskDone(taskId: TaskTable)
}