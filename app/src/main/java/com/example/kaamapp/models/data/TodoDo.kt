package com.example.kaamapp.models.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDo {
    // all data
    // In this function we are not using suspend keyword because flow is async by default
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodoTask():Flow<List<TodoTask>>

    // get task by ID
    @Query("SELECT * FROM todo_table WHERE id=:taskID")
    fun getTodoTaskByID(taskID:Int):Flow<TodoTask>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoTask(todoTask:TodoTask)

    // Update
    @Update
    suspend fun updateTodoTask(todoTask:TodoTask)

    // Delete
    @Delete
    suspend fun deleteTodoTask(todoTask:TodoTask)

    // Delete all
    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodoTask()

    // Search Query
    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchTodoTask(searchQuery:String):Flow<List<TodoTask>>

    // Sort by low priority
    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority():Flow<List<TodoTask>>

    // Sort by High priority
    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority():Flow<List<TodoTask>>
}