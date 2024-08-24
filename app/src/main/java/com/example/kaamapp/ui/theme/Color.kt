package com.example.kaamapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Custom colors
val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

// Extension function
val ColorScheme.topAppBarContentColor : Color
    @Composable
    get() = if(isSystemInDarkTheme()) LightGray else Color.White

val ColorScheme.topAppBarBackgroundColor : Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color.Black else Purple40

val ColorScheme.taskItemBackgroundColor : Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color.DarkGray else Color.White

val ColorScheme.taskTitleColor : Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray

val ColorScheme.addTaskButton : Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color.LightGray else Purple40

val ColorScheme.addTaskButtonOnClick : Color
    @Composable
    get() = if(isSystemInDarkTheme()) MediumGray else PurpleGrey80

val ColorScheme.addTaskButtonContent : Color
    @Composable
    get() = if(isSystemInDarkTheme()) Color.Black else Color.White