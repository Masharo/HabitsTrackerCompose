package com.masharo.habitstrackercompose.ui.screen.colorPicker

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.masharo.habitstrackercompose.model.ColorPickerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val COLORS = 360
private const val SATURATION = 1f
private const val VALUE = 1f

class ColorPickerViewModel(
    private val color: Color? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        value = ColorPickerUiState(
            color = color,
            rgbText = colorToRgb(color),
            hsvText = colorToHsv(color)
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateColor(
        colorPosition: Float,
        widthGradient: Float
    ) {
        val hue = colorPosition / widthGradient * COLORS

        updateColor(
            color = Color.hsv(
                hue = hue,
                saturation = SATURATION,
                value = VALUE
            ),
            hue = hue.toInt()
        )
    }

    fun updateColor(
        color: Color?,
        hue: Int
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                color = color,
                rgbText = colorToRgb(color),
                hsvText = hueToHsv(hue)
            )
        }
    }

    fun updateColor(color: Color?) {
        _uiState.update { currentState ->
            currentState.copy(
                color = color,
                rgbText = colorToRgb(color),
                hsvText = colorToHsv(color)
            )
        }
    }

    private fun hueToHsv(hue: Int) =
        "${hue}, $SATURATION, $VALUE"

    private fun colorToHsv(color: Color?): String {
        if (color == null) return ""
        val hsv = FloatArray(3)

        android.graphics.Color.colorToHSV(
            android.graphics.Color.valueOf(
                color.red,
                color.green,
                color.blue
            ).toArgb(),
            hsv
        )

        return "${hsv[0].toInt()}, ${hsv[1]}, ${hsv[2]}"
    }

    private fun colorToRgb(color: Color?) =
        color?.run {
            "${(red * 255).toInt()}, ${(green * 255).toInt()}, ${(blue * 255).toInt()}"
        } ?: ""

    fun createColorMap(): Brush {
        val colors = mutableListOf<Color>()

        repeat(COLORS) { hue ->
            colors.add(
                Color.hsv(
                    hue = hue.toFloat(),
                    saturation = SATURATION,
                    value = VALUE
                )
            )
        }

        return Brush.horizontalGradient(colors)
    }
}