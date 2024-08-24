package com.example.kaamapp.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaamapp.components.PriorityDropDown
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.ui.theme.addTaskButton
import com.example.kaamapp.ui.theme.addTaskButtonContent
import com.example.kaamapp.ui.theme.addTaskButtonOnClick
import com.example.kaamapp.ui.theme.taskTitleColor
import com.example.kaamapp.ui.theme.topAppBarContentColor
import com.example.kaamapp.utils.Action
import com.example.kaamapp.viewmodels.SharedViewModel

@Composable
fun TaskContent(
    title:String,
    onTitleChange : (String)->Unit,
    priority: Priority,
    onPriorityChange:(Priority)->Unit,
    description:String,
    onDescription:(String)->Unit,
    padVal:PaddingValues,
    isData:Boolean,
    sharedViewModel: SharedViewModel,
    navigateToListTask :(Action)->Unit
){
    val isTitleError:Boolean = sharedViewModel.titleError.value.isNotEmpty()
    val titleErrorMessage = sharedViewModel.titleError.value

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(padVal)
    ) {

        Text(
            text = if(isData) "Task detail" else "Add new task",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.taskTitleColor,

        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange ={
                        onTitleChange(it)
                    },
                    label = {
                        Text(text = "Title")
                    },
                    placeholder = { Text(text = "Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = isTitleError
                )
                if(isTitleError)
                {
                    Text(
                        text = titleErrorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top=10.dp, start = 10.dp)
                    )
                }

            }


            Spacer(
                modifier = Modifier.size(16.dp)
            )
            // Priority
            PriorityDropDown(priority = priority,
                onPrioritySelected = onPriorityChange
            )

            OutlinedTextField(
                value = description,
                onValueChange ={
                               onDescription(it)
                },
                label = {
                    Text(text = "Description")
                },
                placeholder = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(vertical = 10.dp),
                singleLine = false,
                maxLines = 10,
            )

            Button(
                onClick = {
                          val isValid = sharedViewModel.validateTitle()
                    if(isValid && isData){
                        navigateToListTask(Action.UPDATE)
                    }else{
                        navigateToListTask(Action.ADD)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(
                    containerColor= MaterialTheme.colorScheme.addTaskButton,
                    contentColor= MaterialTheme.colorScheme.addTaskButtonContent ,
                    disabledContainerColor= MaterialTheme.colorScheme.addTaskButtonOnClick,
                    disabledContentColor= MaterialTheme.colorScheme.addTaskButtonContent,
                )

            ) {
                Text(
                    text = if(isData) "Update task" else "Add task",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }

    }
}

//@Composable
//@Preview(showBackground = true)
//fun TaskContentPreview(){
//    TaskContent(
//        "",
//        {},
//        Priority.Medium,
//        {},
//        "",
//        {},
//        PaddingValues(4.dp),
//        false,
//        null
//    )
//}