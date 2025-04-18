package com.ae.aetask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ae.aetask.database.dao.TaskDao
import com.ae.aetask.database.table.TaskTable
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [TaskTable::class], version = 4, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var instance: TaskDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun dataBaseClient(context: Context): TaskDatabase {
            synchronized(this) {
                instance = Room
                    .databaseBuilder(context, TaskDatabase::class.java, "task")
                    .fallbackToDestructiveMigration(false)
                    .build()

                return instance!!
            }
        }
    }
}