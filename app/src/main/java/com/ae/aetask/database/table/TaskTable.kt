package com.ae.aetask.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "task_body") val taskDbBody: String

)