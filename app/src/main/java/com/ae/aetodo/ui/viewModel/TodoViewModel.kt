package com.ae.aetodo.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ae.aetodo.repository.TodoRepository
import com.ae.aetodo.ui.model.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val _todos = MutableLiveData<TodoModel>()
    val todos: LiveData<TodoModel> = _todos

    fun getTodos(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _todos.postValue(TodoModel(TodoRepository().getTodos(context)))
        }
    }

    fun createTodo(context: Context, todo: String) {
        TodoRepository().upsertTodo(context, todo)
    }

    fun markTodoDone(context: Context, todoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoRepository().deleteTodo(context, todoId)
        }
    }
}