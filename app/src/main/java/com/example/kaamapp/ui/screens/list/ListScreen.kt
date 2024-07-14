package com.example.kaamapp.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaamapp.R
import com.example.kaamapp.utils.SearchAppBarState
import com.example.kaamapp.viewmodels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen:(Int)->Unit,
    sharedViewModel: SharedViewModel
){
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTask()
    }
//     collectAsState observe database
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState:SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState:String by sharedViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(sharedViewModel,searchAppBarState,searchTextState)
                 },
        content = {
            paddingValues->
            ListContent(allTasks,navigateToTaskScreen,paddingValues)
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

