package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class Priority(
    @StringRes val stringResValue: Int,
    @StringRes val stringResSpinner: Int
) {
    LOW(
        stringResValue = R.string.priority_low,
        stringResSpinner = R.string.priority_low_for_spinner
    ),
    MIDDLE(
        stringResValue = R.string.priority_middle,
        stringResSpinner = R.string.priority_middle_for_spinner
    ),
    HIGH(
        stringResValue = R.string.priority_high,
        stringResSpinner = R.string.priority_high_for_spinner
    )
}