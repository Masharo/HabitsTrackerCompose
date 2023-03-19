package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes

data class HabitListUiState(
    val habitsPositive: List<HabitListItemUiState>,
    val habitsNegative: List<HabitListItemUiState>,
    val countPage: Int,
    @StringRes val pages: Iterable<Int>
)
