package com.example.kaamapp.models.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun toDoDao():TodoDo
}