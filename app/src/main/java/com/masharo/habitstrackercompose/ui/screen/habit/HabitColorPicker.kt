package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.core.view.drawToBitmap
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState

@Composable
internal fun HabitColorPicker(
    uiState: HabitUiState,
    onClickCancel: () -> Unit,
    onClickSave: () -> Unit,
    padding: Dp = 10.dp,
    sizeWindowColor: Dp = 65.dp,
    spaceItems: Dp = 10.dp,
    colors: List<Color> = listOf( //TODO("Цвета")
        Color.Red,
        Color.Yellow
    )
) {
    var selectColor by remember { mutableStateOf(uiState.color) }

    Dialog(
        onDismissRequest = { onClickCancel() }
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
                ) //TODO("цвет фона")
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .drawWithCache {
                            onDrawBehind {
                                drawRoundRect(
                                    brush = Brush.linearGradient(colors),
                                    cornerRadius = CornerRadius(10.dp.toPx())
                                )
                            }
                        }
                        .padding(padding),
                    horizontalArrangement = Arrangement.spacedBy(spaceItems)
                ) {
                    val view = LocalView.current
                    repeat(16) {
                        var positionGradientLine = Offset(0f, 0f)
                        var centerGradientHeight = 0
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
                                    selectColor = Color(
                                        view
                                            .drawToBitmap()
                                            .getColor(
                                                positionGradientLine.x.toInt() + centerGradientWidth,
                                                positionGradientLine.y.toInt() + centerGradientHeight
                                            )
                                            .toArgb()
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
                                    centerGradientHeight = sizes.height / 2
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
                    Button(
                        onClick = { onClickCancel() }
                    ) {
                        Text(text = stringResource(R.string.color_picker_cancel))
                    }
                    Button(
                        onClick = { onClickSave() }//TODO("Сохранить цвет")
                    ) {
                        Text(text = stringResource(R.string.color_picker_save))
                    }
                }
            }
        }
    }
}