package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.Color

data class ColorPickerUiState(
    val color: Color?,

    val rgbText: String = "",
    val hsvText: String = "",

    val countRectangle: Int = 16
)
