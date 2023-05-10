package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.annotation.StringRes
import com.masharo.habitstrackercompose.R

enum class Period(
    @StringRes val stringResValue: Int,
    @StringRes val stringResSpinner: Int
) {
    DAY(
        stringResValue = R.string.period_day_spinner_title,
        stringResSpinner = R.string.period_day
    ),
    WEEK(
        stringResValue = R.string.period_week_spinner_title,
        stringResSpinner = R.string.period_week
    ),
    MOUTH(
        stringResValue = R.string.period_mouth_spinner_title,
        stringResSpinner = R.string.period_mouth
    ),
    YEAR(
        stringResValue = R.string.period_year_spinner_title,
        stringResSpinner = R.string.period_year
    )
}