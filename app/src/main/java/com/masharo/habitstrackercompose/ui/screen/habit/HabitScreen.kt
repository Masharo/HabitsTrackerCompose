package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masharo.core.ui.Spinner
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState
import com.masharo.habitstrackercompose.model.Priority
import com.masharo.habitstrackercompose.model.Type
import com.masharo.habitstrackercompose.ui.screen.colorPicker.ColorPickerDialogScreen
import com.masharo.habitstrackercompose.ui.screen.colorPicker.ColorPickerViewModelFactory

@Composable
fun HabitScreen(
    modifier: Modifier = Modifier,
    vm: HabitViewModel,
    navigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val uiState by vm.uiState.collectAsState()
    var isOpenColorPicker by rememberSaveable { mutableStateOf(false) }

    if (uiState.isError) {
        val message = stringResource(R.string.error_message_input_habit_data)
        val okButton = stringResource(R.string.error_message_ok)

        LaunchedEffect(snackbarHostState) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = okButton,
                duration = SnackbarDuration.Short
            )

            when (snackbarResult) {
                SnackbarResult.Dismissed -> vm.updateIsError(false)
                SnackbarResult.ActionPerformed -> vm.updateIsError(false)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        OutlineTextFieldHabit(
            value = uiState.title,
            onValueChange = {
                vm.updateTitle(it)
            },
            isError = uiState.isTitleError,
            label = R.string.title_habit
        )

        OutlineTextFieldHabit(
            value = uiState.description,
            onValueChange = {
                vm.updateDescription(it)
            },
            isError = uiState.isDescriptionError,
            label = R.string.description_habit,
            singleLine = false
        )

        Spinner(
            title = stringResource(uiState.priority.stringResValue),
            items = Priority.values().map { priority ->
                stringResource(priority.stringResSpinner)
            },
            onSelectItem = { item ->
                vm.updatePriority(Priority.values()[item])
            }
        )

        HabitTypeRadioButtons(vm, uiState)

        OutlineTextFieldHabit(
            value = uiState.count,
            onValueChange = {
                vm.updateCount(it)
            },
            keyboardType = KeyboardType.Number,
            isError = uiState.isCountError,
            label = R.string.need_count
        )

        OutlineTextFieldHabit(
            value = uiState.period,
            onValueChange = {
                vm.updatePeriod(it)
            },
            imeAction = ImeAction.Done,
            isError = uiState.isPeriodError,
            label = R.string.period_input
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpenColorPicker = true
                }
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 25.dp,
                        vertical = 5.dp
                    ),
                text = stringResource(R.string.color_background_habit)
            )
            uiState.color?.let { color ->
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = color,
                            shape = RoundedCornerShape(5.dp)
                        )
                )
            }
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

    if (isOpenColorPicker) {
        ColorPickerDialogScreen(
            onClickSave = { color ->
                vm.updateColor(color)
            },
            dialogClose = {
                isOpenColorPicker = false
            },
            vm = viewModel(
                factory = ColorPickerViewModelFactory(
                    color = uiState.color
                )
            )
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
fun OutlineTextFieldHabit(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        value = value,
        singleLine = singleLine,
        isError = isError,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        label = {
            Text(text = stringResource(label))
        }
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun HabitScreenPreview() {
//    HabitScreen(
//        navigateBack = {
//
//        },
//        snackbarHostState = SnackbarHostState()
//    )
}