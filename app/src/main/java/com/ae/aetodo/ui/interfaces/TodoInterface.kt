package com.ae.aetodo.ui.interfaces

import com.ae.aetodo.database.table.TodoTable

interface TodoInterface {
    fun markTodoDone(todoId: TodoTable)
}