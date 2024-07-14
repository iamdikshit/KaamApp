package com.example.kaamapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.Constant.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list:(Action)->Unit = { action->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN){
                // here inclusive is used to remove task screen from backstack
                inclusive = true
            }
        }
    }

    val task:(Int)->Unit = {
        taskId->
        navController.navigate("task/$taskId")
    }
}