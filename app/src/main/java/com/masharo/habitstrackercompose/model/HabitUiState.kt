package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

data class HabitUiState(
    val title: String = "",
    val description: String = "",
    @StringRes val priority: Int = R.string.priority_low,
    @StringRes val type: Int = R.string.type_negative,
    val count: String = "",
    val countReady: String = "",
    val period: String = ""
)