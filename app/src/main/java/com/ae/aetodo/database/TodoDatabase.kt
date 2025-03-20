package com.ae.aetodo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ae.aetodo.database.dao.TodoDao
import com.ae.aetodo.database.table.TodoTable
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [TodoTable::class], version = 3, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {

        @Volatile
        private var instance: TodoDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun dataBaseClient(context: Context): TodoDatabase {
            synchronized(this) {
                instance = Room
                    .databaseBuilder(context, TodoDatabase::class.java, "todo")
                    .fallbackToDestructiveMigration()
                    .build()

                return instance!!
            }
        }
    }
}