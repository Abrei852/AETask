package com.ae.aetask.repository

import android.content.Context
import com.ae.aetask.database.TaskDatabase
import com.ae.aetask.database.table.TaskTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskRepository {

    private var taskDatabase: TaskDatabase? = null

    private fun initializeDatabase(context: Context): TaskDatabase {
        return TaskDatabase.dataBaseClient(context)
    }

    fun getTasks(context: Context): List<TaskTable> {
        taskDatabase = initializeDatabase(context)
        return taskDatabase!!.taskDao().getDbTasks()
    }

    fun upsertTask(context: Context, body: String) {
        taskDatabase = initializeDatabase(context)

        CoroutineScope(IO).launch {
            val task = TaskTable(0, body)
            taskDatabase!!.taskDao().upsertTask(task)
        }
    }

    fun deleteTask(context: Context, todoId: Int) {
        taskDatabase = initializeDatabase(context)

        CoroutineScope(IO).launch {
            taskDatabase!!.taskDao().deleteTask(todoId)
        }
    }
}