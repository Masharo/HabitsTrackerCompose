package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color

data class HabitListItemUiState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MIDDLE,
    val type: Type = Type.POSITIVE,
    val count: String = "",
    val countReady: String = "0",
    val period: String = "",
    val color: Color? = null
)
