package com.example.kaamapp.navigation.destinations

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.ui.screens.task.TaskScreen
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.Constant.TASK_ARGUMENT_KEY
import com.example.kaamapp.utils.Constant.TASK_SCREEN
import com.example.kaamapp.utils.RequestState
import com.example.kaamapp.viewmodels.SharedViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen:(Action)->Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){
        navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)

        LaunchedEffect(key1 = true ){
            sharedViewModel.getSelectedTask(taskId)
        }

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = selectedTask){

            if(selectedTask is RequestState.Success){
                if((selectedTask as RequestState.Success<TodoTask?>)?.data!=null || taskId==-1)
                {
                    Log.d("selectedTaskCompose","$selectedTask")
                    sharedViewModel.updateSelectedTask(selectedTask = selectedTask)
                }
            }


        }
        TaskScreen(navigateToListTask = navigateToListScreen, selectedTask,sharedViewModel)

    }
}