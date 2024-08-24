package com.example.kaamapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.kaamapp.navigation.destinations.listComposable
import com.example.kaamapp.utils.Constant.LIST_SCREEN
import com.example.kaamapp.viewmodels.SharedViewModel
import com.example.kaamapp.navigation.destinations.taskComposable

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
){
    val screen = remember(navController) {
        Screens(navController)
    }

    NavHost(navController = navController,
        startDestination = LIST_SCREEN
    ){
        // here we define composable screen
        listComposable(
            navigateToTaskScreen = screen.task,sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel
        )
    }
}