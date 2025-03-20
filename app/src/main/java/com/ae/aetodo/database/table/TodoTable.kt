package com.ae.aetodo.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "todo_body") val todoDbBody: String

)