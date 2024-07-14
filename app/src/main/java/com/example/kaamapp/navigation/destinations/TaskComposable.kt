package com.example.kaamapp.navigation.destinations

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kaamapp.ui.screens.task.TaskScreen
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.Constant.TASK_ARGUMENT_KEY
import com.example.kaamapp.utils.Constant.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen:(Action)->Unit
){
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){
        navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        Log.d("taskCom","$taskId")
        TaskScreen(navigateToListTask = navigateToListScreen)

    }
}