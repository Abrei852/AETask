package com.ae.aetask.repository

import android.content.Context
import com.ae.aetask.database.TaskDatabase
import com.ae.aetask.database.table.TaskTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskRepository {

    private var taskDatabase: TaskDatabase? = null

    fun initializeDatabase(context: Context) {
        taskDatabase = TaskDatabase.dataBaseClient(context)
    }

    fun getTasks(): Flow<List<TaskTable>> {
        return taskDatabase!!.taskDao().getDbTasks()
    }

    fun upsertTask(body: String) {

        CoroutineScope(IO).launch {
            val task = TaskTable(0, body)
            taskDatabase!!.taskDao().upsertTask(task)
        }
    }

    fun deleteTask(todoId: Int) {

        CoroutineScope(IO).launch {
            taskDatabase!!.taskDao().deleteTask(todoId)
        }
    }
}