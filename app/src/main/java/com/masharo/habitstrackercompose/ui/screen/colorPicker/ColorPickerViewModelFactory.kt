package com.masharo.habitstrackercompose.ui.screen.colorPicker

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ColorPickerViewModelFactory(
    private val color: Color?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ColorPickerViewModel(
            color = color
        ) as T
}