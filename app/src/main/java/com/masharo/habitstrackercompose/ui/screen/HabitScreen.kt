package com.masharo.habitstrackercompose.ui.screen

import android.view.View
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.drawToBitmap
import com.masharo.habitstrackercompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(
    modifier: Modifier = Modifier
) {

    var selectedType by remember { mutableStateOf(0) }
    var selectedPriority by remember { mutableStateOf(R.string.priority_low_for_spinner) }
    var expanded by remember { mutableStateOf(false) }
    var isOpenColorPicker by remember { mutableStateOf(false) }
    var colorBackground by remember { mutableStateOf(Color.White) }

    val listPriority = listOf(
        R.string.priority_low_for_spinner,
        R.string.priority_middle_for_spinner,
        R.string.priority_high_for_spinner
    )

    val listType = listOf(
        R.string.type_positive_for_radio_button,
        R.string.type_negative_for_radio_button
    )

    //TODO("Изменить кнопку в полях ввода")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Название привычки") //TODO("Перенести в ресурсы")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Описание привычки") //TODO("Перенести в ресурсы")
            }
        )

        //TODO("Вынести в отдельный компонуемый")
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${stringResource(R.string.priority_title)} ${stringResource(selectedPriority)}")
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null
                )
            }

            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listPriority.forEach { idPriority ->
                    DropdownMenuItem(
                        text = {
                            Text(stringResource(idPriority))
                        },
                        onClick = {
                            selectedPriority = idPriority
                            expanded = false
                        }
                    )
                }
            }
        }

        //TODO("Вынести в отдельный компонуемый")
        listType.forEachIndexed { id, type ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedType = id
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = id == selectedType,
                    onClick = {
                        selectedType = id
                    }
                )
                Text(text = stringResource(type))
            }
        }

        //TODO("Должно принимать только int")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {

            },
            label = {
                Text(text = stringResource(R.string.need_count))
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {

            },
            label = {
                Text(text = stringResource(R.string.period_input))
            }
        )

        //Color picker
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpenColorPicker = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Цвет фона") //TODO("В ресурсы")
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Max)
                    .background(color = Color.Blue) //TODO("Должен отображаться выбраный цвет")
            )
        }
    }

    //TODO("Вынести в компонуемый")
    if (isOpenColorPicker) {
        HabitColorPicker(isOpenColorPicker, colorBackground, {}, {})
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HabitColorPicker(
    isOpenColorPicker: Boolean,
    colorBackground: Color,
    cancel: () -> Unit,
    save: () -> Unit,
    padding: Dp = 10.dp,
    sizeWindowColor: Dp = 65.dp,
    spaceItems: Dp = 10.dp,
    colors: List<Color> = listOf( //TODO("Цвета")
        Color.White,
        Color.Red,
        Color.Blue,
        Color.Yellow,
        Color.White
    )
) {
    val view = LocalView.current
    val textHeight = MaterialTheme.typography.titleMedium.lineHeight.value
    var selectColor by remember { mutableStateOf(colorBackground) }

    Dialog(
        onDismissRequest = { cancel() }
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
                    text = "Цвет фона",
                    style = MaterialTheme.typography.titleMedium
                ) //TODO("Вынести в ресурсы")
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
                    colors.forEach { color ->
                        Spacer(
                            modifier = Modifier
                                .size(sizeWindowColor)
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
//                                    selectColor = color
                                    selectColor = Color(view.drawToBitmap().getColor(300, (view.height - 145.dp.value).toInt()).toArgb())
                                }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { cancel() }
                    ) {
                        Text(text = "Отменить") //TODO("Вынести в ресурсы")
                    }
                    Button(
                        onClick = { save() }//TODO("Сохранить цвет")
                    ) {
                        Text(text = "Сохранить") //TODO("Вынести в ресурсы")
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