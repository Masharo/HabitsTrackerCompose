package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes

data class HabitListUiState(
    val habits: List<HabitListItemUiState>,
    val countPage: Int,
    @StringRes val pages: Iterable<Int>
)
