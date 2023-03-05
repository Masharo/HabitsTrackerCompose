package com.masharo.habitstrackercompose.model

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

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