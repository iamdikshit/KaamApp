package com.example.kaamapp.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kaamapp.utils.Constant.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val description:String,
    val priority:Priority
)