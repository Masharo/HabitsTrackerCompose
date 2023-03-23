package com.masharo.habitstrackercompose.model

import androidx.compose.ui.graphics.painter.Painter

data class Contact(
    val img: Painter,
    val desc: String,
    val value: String
)