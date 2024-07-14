package com.example.kaamapp.ui.screens.list
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kaamapp.R
import com.example.kaamapp.components.PriorityItem
import com.example.kaamapp.models.data.Priority
import com.example.kaamapp.ui.theme.topAppBarBackgroundColor
import com.example.kaamapp.ui.theme.topAppBarContentColor
import com.example.kaamapp.utils.SearchAppBarState
import com.example.kaamapp.viewmodels.SharedViewModel

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
){
    when(searchAppBarState){
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value  =
                        SearchAppBarState.OPENED
            },
                onDeleteClicked =  {},
            onSortClicked =     {})
        }
        else->{
        SearchAppBar(
            text= searchTextState,
            onTextChange = {
            sharedViewModel.searchTextState.value = it
        },
            onCloseClicked  = {
                if(sharedViewModel.searchTextState.value.isEmpty()){
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                }
                else{
                    sharedViewModel.searchTextState.value = ""
                }

            },
            onSearchClicked = {}
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: ()->Unit,
    onSortClicked: (priority:Priority)->Unit,
    onDeleteClicked : ()->Unit
){
    TopAppBar(
        title = {
            Text(text = "Task",
                color = MaterialTheme.colorScheme.topAppBarContentColor
                )
        },
        actions = {
            ListAppBarActions(onSearchClicked,onSortClicked,onDeleteClicked)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        )

    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: ()->Unit,
    onSortClicked: (priority:Priority)->Unit,
    onDeleteClicked : ()->Unit
){
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAction(onDeleteClicked)
}
@Composable
fun SearchAction(
    onSearchClicked: ()->Unit
){
    IconButton(onClick = { onSearchClicked() }) {
        Icon(imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_placeholder),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }


}
@Composable
fun SortAction(
    onSortClicked: (priority:Priority)->Unit
){
    var expended by rememberSaveable {
        mutableStateOf(false)
    }
    IconButton(onClick = {  expended = true }) {
        Icon(
            painter = painterResource(id = R.drawable.filter_list),
            contentDescription = "filter icon",
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
        DropdownMenu(expanded = expended , onDismissRequest = { expended = false }) {
            DropdownMenuItem(text ={ PriorityItem(priority = Priority.Low)},
                onClick = {
                    expended = false
                    onSortClicked(Priority.Low)
                })

            DropdownMenuItem(text ={ PriorityItem(priority = Priority.Medium)},
                onClick = {
                    expended = false
                    onSortClicked(Priority.Medium)
                })

            DropdownMenuItem(text ={ PriorityItem(priority = Priority.High)},
                onClick = {
                    expended = false
                    onSortClicked(Priority.High)
                })

            DropdownMenuItem(text ={ PriorityItem(priority = Priority.None)},
                onClick = {
                    expended = false
                    onSortClicked(Priority.None)
                })
        }
    }

}

@Composable
fun DeleteAction(
    onDeleteClicked : ()->Unit
){
    var expended by rememberSaveable {
        mutableStateOf(false)
    }
    IconButton(onClick = { expended = true}) {
        Icon(imageVector = Icons.Filled.MoreVert,
            contentDescription = "Delete action",
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )

        DropdownMenu(expanded = expended , onDismissRequest = { expended = false }) {
            DropdownMenuItem(text ={
                                   Text(text = "Delete All",
                                       modifier = Modifier.padding(start = 12.dp),
                                               style = MaterialTheme.typography.titleSmall,
                                   )
            },
                onClick = {
                    expended = false
                    onDeleteClicked()
                })}

    }
}

@Composable
fun SearchAppBar(
    text:String,
    onTextChange:(String)->Unit,
    onCloseClicked : ()->Unit,
    onSearchClicked:(String)->Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.topAppBarBackgroundColor

    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),

            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    color = Color.White,
                    modifier = Modifier.alpha(.6f),
                )

            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                textDecoration = null
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.topAppBarContentColor

            ),
            // leading Icon

            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(.5f),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription ="search Icon",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },

            // Trailing icon
            trailingIcon = {
                IconButton(onClick = { onCloseClicked() }) {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Clear button",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },

            // Keyboard Icon
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            )
        )
    }
}


@Composable
@Preview
private fun DefaultListAppBarPreview(){
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchAppBarPreview(){
    SearchAppBar("",{},{},{})
}
