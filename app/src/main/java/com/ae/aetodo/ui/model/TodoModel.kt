package com.ae.aetodo.ui.model

import com.ae.aetodo.database.table.TodoTable

class TodoModel(todos: List<TodoTable>) {
    private var todosList = todos

    fun getTodosList(): List<TodoTable> {
        return todosList
    }
}