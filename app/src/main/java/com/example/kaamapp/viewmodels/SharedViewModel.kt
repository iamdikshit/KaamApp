package com.example.kaamapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.models.repository.ToDoRepository
import com.example.kaamapp.utils.RequestState
import com.example.kaamapp.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val toDoRepository:ToDoRepository
):ViewModel() {
     val searchAppBarState:MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
     val searchTextState:MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTasks:StateFlow<RequestState<List<TodoTask>>> = _allTasks
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

    }
}