package com.ae.aetodo.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ae.aetodo.database.table.TodoTable

@Dao
interface TodoDao {
    @Query("SELECT * FROM todotable")
    fun getTodo(): TodoTable

    @Upsert
    fun upsertTodo(vararg todo: TodoTable)
}