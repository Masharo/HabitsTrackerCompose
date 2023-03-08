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
import com.masharo.habitstrackercompose.model.HabitUiState
import com.masharo.habitstrackercompose.model.Priority
import com.masharo.habitstrackercompose.model.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(
    modifier: Modifier = Modifier,
    vm: HabitViewModel = viewModel(),
    navigateBack: () -> Unit
) {

    val uiState by vm.uiState.collectAsState()

    var isOpenColorPicker by remember { mutableStateOf(false) }

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
            isError = uiState.isTitleError,
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
            isError = uiState.isDescriptionError,
            onValueChange = { description ->
                vm.updateDescription(description)
            },
            label = {
                Text(text = stringResource(R.string.description_habit))
            }
        )

        //TODO("Вынести в отдельный компонуемый")
        HabitSpinnerPriorities(
            uiState = uiState,
            onSelectPriority = { priority ->
                vm.updatePriority(priority)
            }
        )

        //TODO("Вынести в отдельный компонуемый")
        HabitTypeRadioButtons(vm, uiState)

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = uiState.count,
            singleLine = true,
            isError = uiState.isCountError,
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
            isError = uiState.isPeriodError,
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
                vm.saveState(navigateBack)
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
        HabitColorPicker(
            uiState = uiState,
            onClickSave = {},
            onClickCancel = {}
        )
    }
}

@Composable
private fun HabitTypeRadioButtons(
    vm: HabitViewModel,
    uiState: HabitUiState
) {
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
}

@Composable
private fun HabitSpinnerPriorities(
    modifier: Modifier = Modifier,
    uiState: HabitUiState,
    onSelectPriority: (priority: Priority) -> Unit
) {
    var isExpandedPriorities by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
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
                        onSelectPriority(priority)
                        isExpandedPriorities = false
                    }
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun HabitScreenPreview() {
    HabitScreen(
        navigateBack = {

        }
    )
}