package com.example.kaamapp.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kaamapp.utils.Action

@Composable
fun TaskScreen(
    navigateToListTask :(Action)->Unit
){
    Scaffold(
        topBar = {
            TaskAppBar(navigateToListTask = navigateToListTask)
        },
        content = {
            it->
            Text(text = "Hello", modifier = Modifier.padding(it))
        }
    )
}