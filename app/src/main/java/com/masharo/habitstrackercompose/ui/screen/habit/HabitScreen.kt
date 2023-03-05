package com.masharo.habitstrackercompose.ui.screen.habit

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
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.drawToBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.Priority
import com.masharo.habitstrackercompose.model.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(
    modifier: Modifier = Modifier,
    vm: HabitViewModel = viewModel()
) {

    val uiState by vm.uiState.collectAsState()

    var isExpandedPriorities by remember { mutableStateOf(false) }
    var isOpenColorPicker by remember { mutableStateOf(false) }
    var colorBackground by remember { mutableStateOf(Color.White) }

    //TODO("Изменить кнопку в полях ввода")
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.title,
            singleLine = true,
            onValueChange = { title ->
                vm.updateTitle(title)
            },
            label = {
                Text(text = stringResource(R.string.name_habit))
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.description,
            onValueChange = { description ->
                vm.updateDescription(description)
            },
            label = {
                Text(text = stringResource(R.string.description_habit))
            }
        )

        //TODO("Вынести в отдельный компонуемый")
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { isExpandedPriorities = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${stringResource(R.string.priority_title)} ${stringResource(uiState.priority.stringResSpinner)}")
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null
                )
            }

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = isExpandedPriorities,
                onDismissRequest = { isExpandedPriorities = false }
            ) {
                Priority.values().forEach { priority ->
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(priority.stringResSpinner))
                        },
                        onClick = {
                            vm.updatePriority(priority)
                            isExpandedPriorities = false
                        }
                    )
                }
            }
        }

        //TODO("Вынести в отдельный компонуемый")
        Type.values().forEach { type ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        vm.updateType(type)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = uiState.type == type,
                    onClick = {
                        vm.updateType(type)
                    }
                )
                Text(text = stringResource(type.stringResRadioButton))
            }
        }

        //TODO("Должно принимать только int")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.count,
            singleLine = true,
            onValueChange = { count ->
                vm.updateCount(count)
            },
            label = {
                Text(text = stringResource(R.string.need_count))
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.period,
            singleLine = true,
            onValueChange = { period ->
                vm.updatePeriod(period)
            },
            label = {
                Text(text = stringResource(R.string.period_input))
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpenColorPicker = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.color_background_habit))
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Max)
                    .background(color = Color.Blue) //TODO("Должен отображаться выбраный цвет")
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Button(
            onClick = {
                //TODO("Добавить сохранение")
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.button_save_habit),
                textAlign = TextAlign.Center
            )
        }
    }

    //TODO("Вынести в компонуемый")
    if (isOpenColorPicker) {
        HabitColorPicker(isOpenColorPicker, colorBackground, {}, {})
    }
}

@Composable
private fun HabitColorPicker(
    isOpenColorPicker: Boolean,
    colorBackground: Color,
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
//    val view = LocalView.current
    var selectColor by remember { mutableStateOf(colorBackground) }

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

@Preview(
    showSystemUi = true
)
@Composable
fun HabitScreenPreview() {
    HabitScreen()
}