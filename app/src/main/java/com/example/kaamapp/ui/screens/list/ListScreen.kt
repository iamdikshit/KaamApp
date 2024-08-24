package com.example.kaamapp.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaamapp.R
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.SearchAppBarState
import com.example.kaamapp.viewmodels.SharedViewModel
import kotlinx.coroutines.launch


@Composable
fun ListScreen(
    navigateToTaskScreen:(Int)->Unit,
    sharedViewModel: SharedViewModel
){
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTask()
//        sharedViewModel.readSortState()
//        Log.d("ReadSort","readSortState")
    }

    val action by sharedViewModel.action
//     collectAsState observe database
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchTask.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState:SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState:String by sharedViewModel.searchTextState

    LaunchedEffect(key1 = searchTextState){
        if(searchTextState.isNotEmpty()){
            sharedViewModel.getSearchedTask(searchTextState)
        }

    }

//    sharedViewModel.handleDatabaseActions(action)
    val snackBarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        scaffoldState = snackBarHostState,
        handleDatabaseActions = {sharedViewModel.handleDatabaseActions(action)},
        taskTitle = sharedViewModel.title.value,
        action = action,
        onUndoClicked={
            sharedViewModel.action.value = it
        }

    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            ListAppBar(sharedViewModel,searchAppBarState,searchTextState)
                 },
        content = {
            paddingValues->
            ListContent(
                allTasks,
                navigateToTaskScreen,
                paddingValues,
                searchedTasks,
                searchAppBarState,
                lowPriorityTasks,
                highPriorityTasks,
                sortState
            )
        },
        floatingActionButton = { ListFab(navigateToTaskScreen)},

    )
}

@Composable
fun ListFab(
    onFabClick:(taskId:Int)->Unit
){
    FloatingActionButton(onClick = {
        onFabClick(-1)
    }) {
        Icon(imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.Black
            )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: SnackbarHostState,
    handleDatabaseActions:()->Unit,
    taskTitle:String,
    action: Action,
    onUndoClicked:(Action)->Unit
){
    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action){
        if(action!=Action.NO_ACTION){

            scope.launch {
                val snackBarResult = scaffoldState.showSnackbar(
                    message = snackBarMessage(action,taskTitle),
                    actionLabel = selectActionLabel(action)
                )
                undoDeletedTask(action,snackBarResult, onUndoClicked)
            }
        }

    }
}

private fun snackBarMessage(
    action: Action,
    title:String
):String{
    return when(action){
        Action.DELETE_ALL->"All task removed."
        else->"${action.name} : $title"
    }
}

private fun selectActionLabel(action:Action):String{
    if(action.name=="DELETE")
    {
        return "Undo"
    }else{
        return "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult:SnackbarResult,
    onUndoClicked:(Action)->Unit
){
    if(snackBarResult==SnackbarResult.ActionPerformed && action==Action.DELETE)
    {
        onUndoClicked(Action.UNDO)
    }
}

