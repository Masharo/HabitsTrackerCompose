package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitType

enum class Type(
    @StringRes val stringResValue: Int,
    @StringRes val stringResRadioButton: Int
) {
    POSITIVE(
        stringResValue = R.string.type_positive,
        stringResRadioButton = R.string.type_positive_for_radio_button
    ),
    NEGATIVE(
        stringResValue = R.string.type_negative,
        stringResRadioButton = R.string.type_negative_for_radio_button
    )
}

fun HabitType.toType() = when(this) {
    HabitType.POSITIVE -> Type.POSITIVE
    HabitType.NEGATIVE -> Type.NEGATIVE
}

fun Type.toHabitType() = when(this) {
    Type.POSITIVE -> HabitType.POSITIVE
    Type.NEGATIVE -> HabitType.NEGATIVE
}