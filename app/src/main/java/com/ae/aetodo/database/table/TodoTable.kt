package com.ae.aetodo.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "todo_title") val todoDbTitle: String,
    @ColumnInfo(name = "todo_body") val todoDbBody: String,
    @ColumnInfo(name = "todo_comment") val todoDbComment: String,
    @ColumnInfo(name = "created_at") val todoDbCreatedAt: String

)