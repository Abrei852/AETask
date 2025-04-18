package com.ae.aetask.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ae.aetask.database.table.TaskTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasktable")
    fun getDbTasks(): Flow<List<TaskTable>>

    @Upsert
    fun upsertTask(vararg task: TaskTable)

    @Query("DELETE FROM tasktable WHERE id = :id")
    fun deleteTask(id: Int)
}