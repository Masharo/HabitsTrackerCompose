package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color
import com.masharo.habitstrackercompose.ui.screen.habit.Priority
import com.masharo.habitstrackercompose.ui.screen.habit.Type

data class HabitListItemUiState(
    val id: Long,
    val title: String = "",
    private val _description: String = "",
    val priority: Priority = Priority.MIDDLE,
    val type: Type = Type.POSITIVE,
    val count: String = "",
    val countReady: String = "0",
    val period: String = "",
    val color: Color? = null
) {
    val description = if (_description.length < 50) _description else "${_description.substring(0, 50)}..."
}
