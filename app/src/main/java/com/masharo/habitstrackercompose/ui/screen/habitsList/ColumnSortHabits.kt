package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.ColumnSort

enum class ColumnSortHabits(
    @StringRes val title: Int,
    @StringRes val selectedTitle: Int
) {

    PRIORITY(
        title = R.string.column_priority_variant,
        selectedTitle = R.string.column_priority_selected
    ),

    COUNT(
        title = R.string.column_count_variant,
        selectedTitle = R.string.column_count_selected
    ),

    ID(
        title = R.string.column_id_variant,
        selectedTitle = R.string.column_id_selected
    );

    companion object {

        fun defaultValue() = ID

    }

}

fun ColumnSortHabits.toColumnSort() = when(this) {
    ColumnSortHabits.PRIORITY   -> ColumnSort.PRIORITY
    ColumnSortHabits.COUNT      -> ColumnSort.COUNT
    ColumnSortHabits.ID         -> ColumnSort.ID
}