package com.example.kaamapp.models.data

import androidx.compose.ui.graphics.Color

enum class Priority(val color: Color) {
    High(Color.Red),
    Medium(Color.Yellow),
    Low(Color.Green),
    None(Color.White)
}