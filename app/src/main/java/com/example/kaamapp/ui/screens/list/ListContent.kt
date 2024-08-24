package com.example.kaamapp.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.ui.theme.MediumGray
import com.example.kaamapp.ui.theme.taskItemBackgroundColor
import com.example.kaamapp.ui.theme.taskTitleColor
import com.example.kaamapp.utils.RequestState
import com.example.kaamapp.utils.SearchAppBarState

@Composable
fun ListContent(
    tasks:RequestState<List<TodoTask>>,
    navigationToTaskScreen:(taskId:Int)->Unit,
    paddingValues: PaddingValues,
    searchedTask:RequestState<List<TodoTask>>,
    searchAppBarState: SearchAppBarState,
    lowPriorityTasks : List<TodoTask>,
    highPriorityTasks : List<TodoTask>,
    sortState:RequestState<Priority>
){
    if(sortState is RequestState.Success)
    {
        when{
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if(searchedTask is RequestState.Success)
                {
                    HandleTask(tasks = searchedTask.data,
                        navigationToTaskScreen =navigationToTaskScreen ,
                        paddingValues =paddingValues )
                }
            }
            sortState.data == Priority.None->{
                if (tasks is RequestState.Success)
                {
                    HandleTask(tasks = tasks.data,
                        navigationToTaskScreen =navigationToTaskScreen ,
                        paddingValues =paddingValues )
                }
            }
            sortState.data == Priority.Low ->{
                HandleTask(tasks = lowPriorityTasks,
                    navigationToTaskScreen =navigationToTaskScreen ,
                    paddingValues =paddingValues )
            }
            sortState.data == Priority.High->{
                HandleTask(tasks = highPriorityTasks,
                    navigationToTaskScreen =navigationToTaskScreen ,
                    paddingValues =paddingValues )
            }
        }
    }

}

@Composable
fun HandleTask(
    tasks: List<TodoTask>,
    navigationToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
){
        if (tasks.isEmpty())
        {
            EmptyContent()
        }
        else{
            DisplayTasks(tasks,navigationToTaskScreen,paddingValues)
        }
}

@Composable
fun DisplayTasks(
    tasks:List<TodoTask>,
    navigationToTaskScreen:(taskId:Int)->Unit,
    paddingValues: PaddingValues
){
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ){
        items(tasks){
                task->
            TaskItem(toDoTask = task, navigationToTaskScreen = navigationToTaskScreen)
        }
    }
}

@Composable
fun TaskItem(
    toDoTask:TodoTask,
    navigationToTaskScreen:(taskId:Int)->Unit
){
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = 2.dp,
        onClick = {
            navigationToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskTitleColor,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ){
                    Canvas(
                        modifier=Modifier.size(16.dp),
                        ){
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }


            }

            Text(
                text =toDoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = MediumGray,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview
fun TaskItemPreview(){
    TaskItem(toDoTask = TodoTask(
        id = 0,
        title = "abc",
        description = "some random text",
        priority = Priority.Medium
    ) , navigationToTaskScreen = {  })
}