package com.example.kaamapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.models.repository.DataStoreRepository
import com.example.kaamapp.models.repository.ToDoRepository
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.RequestState
import com.example.kaamapp.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository:ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
):ViewModel() {
    // Variable for action
    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

     val searchAppBarState:MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
     val searchTextState:MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTasks:StateFlow<RequestState<List<TodoTask>>> = _allTasks

    private val _searchTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val searchTask:StateFlow<RequestState<List<TodoTask>>> = _searchTasks

    // variables for all todo data fields
    val id = mutableIntStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.Low)

    // Variable for validation

    var titleError = mutableStateOf("")

    fun getAllTask(){
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTask.collect{
                    _allTasks.value = RequestState.Success(it)
                }
            }
        }catch (e:Exception){
            _allTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED

    }

    fun getSearchedTask(searchQuery:String){
        _searchTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                toDoRepository.searchDatabase(searchQuery).collect{
                    _searchTasks.value = RequestState.Success(it)
                }
            }
        }catch (e:Exception){
            _searchTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState = _sortState
    val lowPriorityTasks:StateFlow<List<TodoTask>> = toDoRepository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTasks:StateFlow<List<TodoTask>> = toDoRepository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

//    fun readSortState(){
//        _sortState.value = RequestState.Loading
//        try {
//            viewModelScope.launch {
//                dataStoreRepository.readSortState.map {
//                    Log.d("Priority","$it")
////                    Priority.valueOf(it.toString())
//                }.collect{
////                    _sortState.value = RequestState.Success(it)
//                }
//            }
//        }catch (e:Exception){
//            _sortState.value = RequestState.Error(e)
//        }
//        searchAppBarState.value = SearchAppBarState.CLOSED
//    }
    fun persistSortingState(priority: Priority){
        viewModelScope.launch(Dispatchers.IO){
            dataStoreRepository.persistStoreState(priority)
        }
    }

    /*
    * Developer : Dikshit Bhardwaj
    * Date : 14-July-2023
    * Feature : function getSelectedTask : this function will take parameter taskId
    * and will return task having same id as taskId
    */

    private val _selectedTask:MutableStateFlow<RequestState<TodoTask?>> = MutableStateFlow(RequestState.Idle)
    val selectedTask : StateFlow<RequestState<TodoTask?>> = _selectedTask
    fun getSelectedTask(taskId:Int){
        _selectedTask.value = RequestState.Loading
        try {
            if(taskId==-1)
            {
                _selectedTask.value = RequestState.Success(null)
            }else{
                viewModelScope.launch {
                    toDoRepository.getSelectedTask(taskId).collect{task->
                        _selectedTask.value = RequestState.Success(task)
                    }
                }
            }

        }
        catch (e:Exception){
            _selectedTask.value = RequestState.Error(e)
        }

    }

    /*
   * Developer : Dikshit Bhardwaj
   * Date : 20-July-2023
   * Feature : function updateSelectedTask : this function will take parameter selectedTask
   * will update all the field of todotask
   */

    fun updateSelectedTask(selectedTask:RequestState<TodoTask?>){
        if(selectedTask is RequestState.Success)
        {
            if(selectedTask.data!=null){
                id.value = selectedTask.data.id
                title.value = selectedTask.data.title
                description.value = selectedTask.data.description
                priority.value = selectedTask.data.priority
            }else{
                // Default value
                id.value = -1
                title.value = ""
                description.value = ""
                priority.value = Priority.Low
            }
        }

    }

    /*
   * Developer : Dikshit Bhardwaj
   * Date : 20-July-2023
   * Feature : function validateTitle : this function will validate title field
   */

    fun validateTitle():Boolean{
        val dummyTitle = title
        var errorMessage = ""
        var isValid = true

        if(dummyTitle.value.length > 20)
        {
            isValid = false
            errorMessage = "Title cannot be more than 20 character."
        }
        else if( dummyTitle.value.isEmpty() || dummyTitle.value.isBlank())
        {
            isValid = false
            errorMessage = "Title cannot empty."
        }
        titleError.value = errorMessage
        return isValid
    }

    private fun addTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = TodoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.addTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO){
            val toDoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.updateTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO){
            val toDoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            toDoRepository.deleteTask(toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO){
            toDoRepository.deleteAllTask()
        }
    }

    // Database action
    fun handleDatabaseActions(action: Action){
        when(action){
            Action.ADD->addTask()
            Action.UPDATE ->updateTask()
            Action.DELETE->deleteTask()
            Action.UNDO->addTask()
            Action.DELETE_ALL->deleteAllTask()
            else -> {}
        }
        // make action as no action
        this.action.value = Action.NO_ACTION
    }


}