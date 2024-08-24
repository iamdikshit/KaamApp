package com.example.kaamapp.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaamapp.models.data.Priority

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected:(Priority)->Unit
){
    var expended by rememberSaveable {
        mutableStateOf(false)
    }

    val angle:Float by animateFloatAsState(
        targetValue = if(expended) 180f else 0f, label = "arrow"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = .5f
                    )
                ),
                shape = MaterialTheme.shapes.extraSmall
            ).clickable {
                        expended = true
            },
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .weight(1f)
        ){
            drawCircle(
                color = priority.color
            )
        }

        // Text
        Text(
            modifier = Modifier.weight(8f),
            text = priority.name,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )

        // Icon
        IconButton(
            onClick = {
                      expended = true
                      },
            modifier = Modifier
                .weight(1f)
                .rotate(angle),
        ) {
            Icon(imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "")
            
        }

        // Dropdown menu icons
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(0.92f),
            expanded = expended,
            onDismissRequest = {
            expended = false
        }) {
            DropdownMenuItem(
                    text = {
                        PriorityItem(priority = Priority.Low)
                    },
            onClick = {
                expended = false
                onPrioritySelected(Priority.Low)
            })

            DropdownMenuItem(
                text = {
                    PriorityItem(priority = Priority.Medium)
                },
                onClick = {
                    expended = false
                    onPrioritySelected(Priority.Medium)
                })

            DropdownMenuItem(
                text = {
                    PriorityItem(priority = Priority.High)
                },
                onClick = {
                    expended = false
                    onPrioritySelected(Priority.High)
                })
        }

    }
}

@Composable
@Preview(showBackground = true)
fun PriorityDropDownPreview(){
    PriorityDropDown(Priority.Low,{})
}