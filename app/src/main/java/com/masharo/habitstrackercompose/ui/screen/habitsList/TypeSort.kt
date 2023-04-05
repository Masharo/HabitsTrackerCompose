package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class TypeSort(
    @StringRes val title: Int,
    @StringRes val selectedTitle: Int
) {
    ASC(
        title = R.string.type_sort_asc_variant,
        selectedTitle = R.string.type_sort_asc_selected
    ) {
        override fun getValue() = true
    },
    DESC(
        title = R.string.type_sort_desc_variant,
        selectedTitle = R.string.type_sort_desc_selected
    ) {
        override fun getValue() = false
    };

    abstract fun getValue(): Boolean

    companion object {

        fun getTypeSort(isAsc: Boolean) = if (isAsc) ASC else DESC

        fun defaultValue() = ASC

    }
}