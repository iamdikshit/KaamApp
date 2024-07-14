package com.example.kaamapp.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kaamapp.ui.screens.list.ListScreen
import com.example.kaamapp.utils.Constant.LIST_ARGUMENT_KEY
import com.example.kaamapp.utils.Constant.LIST_SCREEN
import com.example.kaamapp.viewmodels.SharedViewModel


// Here we are creating extension function of NavGraphBuilder
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen:(Int)->Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
        })
    ){
        // list screen ui
        ListScreen(navigateToTaskScreen,sharedViewModel)
    }
}