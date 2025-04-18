package com.ae.aetask.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ae.aetask.database.table.TaskTable
import com.ae.aetask.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    val repo = TaskRepository()

    fun initializeDb(context: Context) {
        repo.initializeDatabase(context = context)
    }

    fun getTasks(): Flow<List<TaskTable>> {
        return repo.getTasks()
    }

    fun createTask(task: String) {
        repo.upsertTask(task)
    }

    fun markTaskDone(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTask(taskId)
        }
    }
}