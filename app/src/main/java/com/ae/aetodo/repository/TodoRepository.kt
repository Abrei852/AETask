package com.ae.aetodo.repository

import android.content.Context
import com.ae.aetodo.database.TodoDatabase
import com.ae.aetodo.database.table.TodoTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TodoRepository {

    private var todoDatabase: TodoDatabase? = null

    private fun initializeDatabase(context: Context): TodoDatabase {
        return TodoDatabase.dataBaseClient(context)
    }

    fun getTodos(context: Context): List<TodoTable> {
        todoDatabase = initializeDatabase(context)
        return todoDatabase!!.todoDao().getDbTodos()
    }

    fun upsertTodo(context: Context, body: String) {
        todoDatabase = initializeDatabase(context)

        CoroutineScope(IO).launch {
            val todo = TodoTable(0, body)
            todoDatabase!!.todoDao().upsertTodo(todo)
        }
    }

    fun deleteTodo(context: Context, todoId: Int) {
        todoDatabase = initializeDatabase(context)

        CoroutineScope(IO).launch {
            todoDatabase!!.todoDao().deleteTodo(todoId)
        }
    }
}