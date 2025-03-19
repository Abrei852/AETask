package com.ae.aetodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ae.aetodo.database.dao.TodoDao
import com.ae.aetodo.database.table.TodoTable

@Database(entities = [TodoTable::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}