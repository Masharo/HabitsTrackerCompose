package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState

@Composable
internal fun HabitColorPicker(
    uiState: HabitUiState,
    dialogClose: () -> Unit,
    onClickSave: (Color?) -> Unit,
    padding: Dp = 10.dp,
    sizeWindowColor: Dp = 65.dp,
    spaceItems: Dp = 10.dp
) {
    var selectColor by remember { mutableStateOf(uiState.color) }
    var widthGradient = 0
    val scrollStateGradient = rememberScrollState()
    val colorMap = createColorMap()
    val modifierSelectColor = selectColor?.let { color ->
        Modifier
            .background(
                color = color ,
                shape = RoundedCornerShape(10.dp)
            )
    } ?: Modifier

    Dialog(
        onDismissRequest = {
            dialogClose()
        }
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
                    modifier = modifierSelectColor
                        .size(sizeWindowColor)
                )
                Row() {
                    //TODO()
                    selectColor?.let { color ->
                        val hsv = FloatArray(3)
                        android.graphics.Color.colorToHSV(
                            android.graphics.Color.valueOf(
                                color.red,
                                color.green,
                                color.blue
                            ).toArgb(),
                            hsv
                        )

                        //rgb
                        Text(
                            stringResource(
                                R.string.rgb_template,
                                (color.red * 255).toInt(),
                                (color.green * 255).toInt(),
                                (color.blue * 255).toInt()
                            )
                        )

                        //hsv
                        Text(
                            stringResource(
                                R.string.hsv_template,
                                hsv[0].toInt(),
                                hsv[1],
                                hsv[2]
                            )
                        )
                    } ?: run {
                        Text(stringResource(R.string.not_color))
                    }
                }
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
                                    val widthLocateClick =
                                        scrollStateGradient.value + positionGradientLine.x
                                    +centerGradientWidth - padding.value
                                    selectColor = Color.hsv(
                                        hue = widthLocateClick / widthGradient * 360f,
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
                        onClick = {
                            selectColor = null
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.color_picker_default_color),
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    TextButton(
                        onClick = {
                            onClickSave(selectColor)
                            dialogClose()
                        }//TODO("Сохранить цвет")
                    ) {
                        Text(
                            text = stringResource(R.string.color_picker_save),
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall
                        )
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

    repeat(360) { hue ->
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