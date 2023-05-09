package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class ColumnSort(
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