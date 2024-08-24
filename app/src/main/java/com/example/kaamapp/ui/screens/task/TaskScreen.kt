package com.example.kaamapp.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.ui.theme.taskTitleColor
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.RequestState
import com.example.kaamapp.viewmodels.SharedViewModel

@Composable
fun TaskScreen(
    navigateToListTask :(Action)->Unit,
    selectedTask:RequestState<TodoTask?>,
    sharedViewModel: SharedViewModel
){
    val title:String by sharedViewModel.title
    val description:String by sharedViewModel.description
    val priority:Priority by sharedViewModel.priority
    val id:Int by sharedViewModel.id


    Scaffold(
        topBar = {
            TaskAppBar(navigateToListTask = {
                                            action ->
                                            if(action==Action.NO_ACTION)
                                            {
                                                sharedViewModel.titleError.value = ""
                                                navigateToListTask(action)
                                            }else{
                                               if(sharedViewModel.validateTitle()){
                                                   navigateToListTask(action)
                                               }
                                            }
            },selectedTask)
        },
        content = {
            it->
                    TaskContent(
                        title =title ,
                        onTitleChange = {
                            sharedViewModel.title.value = it
                        },
                        priority =priority ,
                        onPriorityChange = {
                            sharedViewModel.priority.value = it
                        },
                        description = description,
                        onDescription ={
                            sharedViewModel.description.value = it
                        } ,
                        padVal = it,
                        isData = id != -1,
                        sharedViewModel,
                        navigateToListTask
                    )



        }
    )
}

