package com.ae.aetask.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ae.aetask.repository.TaskRepository
import com.ae.aetask.ui.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<TaskModel>()
    val tasks: LiveData<TaskModel> = _tasks

    fun getTasks(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.postValue(TaskModel(TaskRepository().getTasks(context)))
        }
    }

    fun createTask(context: Context, todo: String) {
        TaskRepository().upsertTask(context, todo)
    }

    fun markTaskDone(context: Context, todoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            TaskRepository().deleteTask(context, todoId)
        }
    }
}