package com.example.kaamapp.models.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String):Priority{
        return Priority.valueOf(priority)
    }
}