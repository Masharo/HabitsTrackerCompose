package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitSort

data class HabitListUiState(
    val habitsPositive: List<HabitListItemUiState>,
    val habitsNegative: List<HabitListItemUiState>,
    val countPage: Int,
    val habitSort: HabitSort,
    @StringRes val pages: Iterable<Int>
)
