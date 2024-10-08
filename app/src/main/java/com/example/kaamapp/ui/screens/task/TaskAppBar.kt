package com.example.kaamapp.ui.screens.task

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaamapp.R
import com.example.kaamapp.components.DialogAlertBox
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.models.data.TodoTask
import com.example.kaamapp.ui.theme.topAppBarBackgroundColor
import com.example.kaamapp.ui.theme.topAppBarContentColor
import com.example.kaamapp.utils.Action
import com.example.kaamapp.utils.RequestState

@Composable
fun TaskAppBar(
    navigateToListTask :(Action)->Unit,
    selectedTask: RequestState<TodoTask?>
){

    if(selectedTask is RequestState.Success && selectedTask.data!=null)
    {
        if(selectedTask.data?.id == -1){
            Log.d("selectedTaskTest","$selectedTask")
            NewTaskAppBar(navigateToListTask)
        }else{
            ExistingTaskAppBar(selectedTask.data,navigateToListTask)
        }
    }else
    {
        NewTaskAppBar(navigateToListTask)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListTask :(Action)->Unit
){
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked =navigateToListTask )
        },
        title = {
            Text(text = "Add Task")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
            scrolledContainerColor=MaterialTheme.colorScheme.topAppBarContentColor ,
            navigationIconContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
            titleContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
            actionIconContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
        ),
        actions = {
            AddAction(navigateToListTask)

        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    selectedTask : TodoTask?,
    navigateToListTask :(Action)->Unit
){
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked =navigateToListTask )
        },
        title = {
            if (selectedTask != null) {
                Text(
                    text = selectedTask.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
            scrolledContainerColor=MaterialTheme.colorScheme.topAppBarContentColor ,
            navigationIconContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
            titleContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
            actionIconContentColor= MaterialTheme.colorScheme.topAppBarContentColor,
        ),
        actions = {
            ExistingTaskAppBarAction(
                selectedTask,
                navigateToListTask
            )

        }
    )
}

@Composable
fun ExistingTaskAppBarAction(
    selectedTask : TodoTask?,
    navigateToListTask: (Action) -> Unit
){
    var openDialog by remember{
        mutableStateOf(false)
    }
    DialogAlertBox(
        title = stringResource(
            id = R.string.delete_task,
            selectedTask?.title as Any
        ),
        text = stringResource(
            id = R.string.delete_task_confirmation,
            selectedTask?.title as Any
        ),
        onConfirmation = { navigateToListTask(Action.DELETE) },
        onDismiss = {
                    openDialog = false
        },
        isOpen = openDialog
    )
    DeleteAction { openDialog = true }
    UpdateAction(navigateToListTask)
}

@Composable
fun BackAction(
    onBackClicked:(Action)->Unit
){
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack,
            contentDescription =""
        )

    }
}

@Composable
fun CloseAction(
    onCloseClicked:(Action)->Unit
){
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Clear,
            contentDescription ="Close Icon"
        )

    }
}

@Composable
fun AddAction(
    onAddClicked:(Action)->Unit
){
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription ="Add task"
        )

    }
}

@Composable
fun DeleteAction(
    onDeleteClicked:()->Unit
){
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(imageVector = Icons.Filled.Delete,
            contentDescription ="Delete Icon"
        )

    }
}

@Composable
fun UpdateAction(
    onUpdateClicked:(Action)->Unit
){
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription ="Update task"
        )

    }
}


@Composable
@Preview
fun NewTaskAppBarPreview(){
    NewTaskAppBar({})
}

@Composable
@Preview
fun ExistingTaskAppBarPreview(){
    ExistingTaskAppBar(
        TodoTask(0,"Hello","Testing chal raha hai",Priority.High),
        {}
    )
}