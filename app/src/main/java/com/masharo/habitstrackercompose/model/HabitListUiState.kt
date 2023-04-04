package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.ui.screen.habitsList.ColumnSort
import com.masharo.habitstrackercompose.ui.screen.habitsList.TypeSort

data class HabitListUiState(
    val habitsPositive: List<HabitListItemUiState>,
    val habitsNegative: List<HabitListItemUiState>,

    //Pager
    val countPage: Int,
    @StringRes val pages: Iterable<Int>,

    //BottomSheet
    val search: String,
    val typeSort: TypeSort,
    val columnSort: ColumnSort
)
