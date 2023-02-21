package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

data class Habit(
    val title: String = "",
    val description: String = "",
    @StringRes val priority: Int = R.string.priority_low,
    @StringRes val type: Int = R.string.type_negative,
    val count: Int = 0,
    val countReady: Int = 0,
    @StringRes val period: Int = R.string.period_day
)