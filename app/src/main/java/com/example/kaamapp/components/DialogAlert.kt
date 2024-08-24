package com.example.kaamapp.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAlertBox(
    title:String,
    text:String,
    onConfirmation:()->Unit,
    onDismiss:()->Unit,
    isOpen:Boolean
){
    if(isOpen){
        AlertDialog(
            title = {
                    Text(
                        text = title,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )
            },
            text = {
                   Text(
                       text = text,
                       fontSize = MaterialTheme.typography.bodyMedium.fontSize
                   )
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {

                Button(onClick = {
                    onConfirmation()
                    onDismiss()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = "No")
                }
            }
        )
    }
}

@Preview
@Composable
fun DialogAlertBoxPreview(){
    DialogAlertBox(
        title = "Delete Task ?",
        text = "Are you sure want to remove task?",
        onConfirmation = { /*TODO*/ },
        onDismiss = { /*TODO*/ },
        isOpen = true
    )
}