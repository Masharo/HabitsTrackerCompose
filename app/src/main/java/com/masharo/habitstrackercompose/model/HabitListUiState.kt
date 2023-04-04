package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.ui.screen.habitsList.ColumnSort

data class HabitListUiState(
    val habitsPositive: List<HabitListItemUiState>,
    val habitsNegative: List<HabitListItemUiState>,

    //Pager
    val countPage: Int,
    @StringRes val pages: Iterable<Int>,

    //BottomSheet
    val search: String,
    val isAsc: Boolean,
    val columnSort: ColumnSort
)
