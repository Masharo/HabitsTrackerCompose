package com.masharo.habitstrackercompose.model

data class HabitUiState(
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.MIDDLE,
    val type: Type = Type.POSITIVE,
    val count: String = "",
    val countReady: String = "0",
    val period: String = "",

    val isTitleError: Boolean = false,
    val isDescriptionError: Boolean = false,
    val isCountError: Boolean = false,
    val isCountReadyError: Boolean = false,
    val isPeriodError: Boolean = false
)