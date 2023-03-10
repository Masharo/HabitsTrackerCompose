package com.masharo.habitstrackercompose.ui.screen.habit

import android.graphics.Color.HSVToColor
import androidx.annotation.FloatRange
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState

@Composable
internal fun HabitColorPicker(
    uiState: HabitUiState,
    onClickDefaultColor: () -> Unit,
    onClickSave: () -> Unit,
    padding: Dp = 10.dp,
    sizeWindowColor: Dp = 65.dp,
    spaceItems: Dp = 10.dp
) {
    var selectColor by remember { mutableStateOf(uiState.color) }
    var widthGradient = 0
    val scrollStateGradient = rememberScrollState()
    val colorMap = createColorMap()

    Dialog(
        onDismissRequest = { onClickDefaultColor() }
    ) {
        Card(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(spaceItems),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.color_background_habit),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(
                    modifier = Modifier
                        .size(sizeWindowColor)
                        .background(
                            color = selectColor,
                            shape = RoundedCornerShape(10.dp)
                        )
                )
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollStateGradient)
                        .drawWithCache {
                            onDrawBehind {
                                drawRoundRect(
                                    brush = colorMap,
                                    cornerRadius = CornerRadius(10.dp.toPx())
                                )
                            }
                        }
                        .onSizeChanged {
                            widthGradient = it.width
                        }
                        .padding(padding),
                    horizontalArrangement = Arrangement.spacedBy(spaceItems)
                ) {
                    val view = LocalView.current
                    repeat(16) {
                        var positionGradientLine = Offset(0f, 0f)
                        var centerGradientWidth = 0

                        Spacer(
                            modifier = Modifier
                                .size(sizeWindowColor)
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { //TODO("Перенести в вм")
                                    val widthLocateClick = scrollStateGradient.value + positionGradientLine.x
                                    + centerGradientWidth - padding.value
                                    selectColor = Color.hsv(
                                        hue = widthLocateClick / widthGradient * 10f,
                                        saturation = 1f,
                                        value = 1f
                                    )
                                }
                                .onGloballyPositioned { coords ->
                                    coords.parentCoordinates
                                        ?.positionInWindow()
                                        ?.let { offset ->
                                            positionGradientLine = offset
                                        }
                                }
                                .onSizeChanged { sizes ->
                                    centerGradientWidth = sizes.width / 2
                                }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        onClick = { onClickDefaultColor() }
                    ) {
                        Text(text = stringResource(R.string.color_picker_default_color))
                    }
                    TextButton(
                        onClick = { onClickSave() }//TODO("Сохранить цвет")
                    ) {
                        Text(text = stringResource(R.string.color_picker_save))
                    }
                }
            }
        }
    }
}

private fun createColorMap(
    saturation: Float = 1f,
    lightness: Float = 1f
): Brush {
    val colors = mutableListOf<Color>()

    repeat(10) { hue ->
        colors.add(
            Color.hsv(
                hue = hue.toFloat(),
                saturation = saturation,
                value = lightness
            )
        )
    }

    return Brush.horizontalGradient(colors)
}