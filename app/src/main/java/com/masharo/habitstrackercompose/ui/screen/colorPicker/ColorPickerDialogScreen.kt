package com.masharo.habitstrackercompose.ui.screen.colorPicker

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.ColorPickerUiState

@Composable
internal fun ColorPickerDialogScreen(
    vm: ColorPickerViewModel = viewModel(),
    dialogClose: () -> Unit,
    onClickSave: (Color?) -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    val modifierSelectColor = uiState.color?.let { color ->
        Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            )
    } ?: Modifier

    val padding: Dp = 10.dp
    val sizeWindowColor: Dp = 65.dp
    val spaceItems: Dp = 10.dp

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
                        .size(65.dp)
                )

                SelectColorInfo(
                    rgbText = uiState.rgbText,
                    hsvText = uiState.hsvText
                )

                GradientLine(
                    vm = vm,
                    padding = padding,
                    spaceItems = spaceItems,
                    uiState = uiState,
                    sizeWindowColor = sizeWindowColor
                )

                ButtonsSaveOrDefaultColor(
                    updateColorToDefaultValue = {
                        vm.updateColor(null)
                    },
                    save = {
                        onClickSave(uiState.color)
                        dialogClose()
                    }
                )
            }
        }
    }
}

@Composable
private fun SelectColorInfo(
    modifier: Modifier = Modifier,
    rgbText: String,
    hsvText: String
) {
    Row(
        modifier = modifier
    ) {
        //rgb
        Text(
            text = stringResource(R.string.rgb_template, rgbText),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.weight(1f))
        //hsv
        Text(
            text = stringResource(R.string.hsv_template, hsvText),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ButtonsSaveOrDefaultColor(
    modifier: Modifier = Modifier,
    updateColorToDefaultValue: () -> Unit,
    save: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(
            onClick = updateColorToDefaultValue
        ) {
            Text(
                text = stringResource(R.string.color_picker_default_color),
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall
            )
        }
        TextButton(
            onClick = save
        ) {
            Text(
                text = stringResource(R.string.color_picker_save),
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun GradientLine(
    modifier: Modifier = Modifier,
    vm: ColorPickerViewModel,
    padding: Dp,
    spaceItems: Dp,
    uiState: ColorPickerUiState,
    sizeWindowColor: Dp
) {
    var widthGradient = 0
    val scrollStateGradient = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollStateGradient)
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        brush = vm.createColorMap(),
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
        repeat(uiState.countRectangle) {
            ColorItem(
                sizeWindowColor = sizeWindowColor,
                scrollStateGradient = scrollStateGradient,
                padding = padding
            ) { colorPosition ->
                vm.updateColor(
                    colorPosition = colorPosition,
                    widthGradient = widthGradient.toFloat()
                )
            }
        }
    }
}

@Composable
private fun ColorItem(
    modifier: Modifier = Modifier,
    sizeWindowColor: Dp,
    scrollStateGradient: ScrollState,
    padding: Dp,
    updateColor: (colorPosition: Float) -> Unit
) {
    var positionGradientLine = 0f
    var centerGradientWidth = 0

    Spacer(
        modifier = modifier
            .size(sizeWindowColor)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                val widthLocateClick =
                    scrollStateGradient.value + positionGradientLine
                    + centerGradientWidth - padding.value
                updateColor(widthLocateClick)
            }
            .onGloballyPositioned { coords ->
                positionGradientLine = coords
                    .parentCoordinates
                    ?.positionInWindow()
                    ?.x ?: 0f
            }
            .onSizeChanged { sizes ->
                centerGradientWidth = sizes.width / 2
            }
    )
}