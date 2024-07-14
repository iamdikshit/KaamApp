package com.example.kaamapp.models.repository

import com.example.kaamapp.models.data.TodoDo
import com.example.kaamapp.models.data.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
    private val todoDao: TodoDo
) {
    val getAllTask:Flow<List<TodoTask>> = todoDao.getAllTodoTask()
    val sortByLowPriority:Flow<List<TodoTask>> = todoDao.sortByLowPriority()
    val sortByHighPriority:Flow<List<TodoTask>> = todoDao.sortByHighPriority()

    fun getSelectedTask(taskID:Int):Flow<TodoTask>{
        return todoDao.getTodoTaskByID(taskID)
    }

    suspend fun addTask(todoTask:TodoTask){
        todoDao.insertTodoTask(todoTask)
    }
    suspend fun updateTask(todoTask:TodoTask){
        todoDao.updateTodoTask(todoTask)
    }
    suspend fun deleteTask(todoTask:TodoTask){
        todoDao.deleteTodoTask(todoTask)
    }

    suspend fun deleteAllTask(){
        todoDao.deleteAllTodoTask()
    }

    fun searchDatabase(searchQuery:String):Flow<List<TodoTask>>{
        return todoDao.searchTodoTask(searchQuery)
    }

}