package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.masharo.core.ui.OutlineTextFieldMinimalistic
import com.masharo.core.ui.Spinner
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState
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

    if (uiState.isError) ShowNotValidInputSnackbar(
        snackbarHostState = snackbarHostState,
        updateStatus = vm::updateIsError
    )

    if (isOpenColorPicker) {
        ColorPickerDialogScreen(
            onClickSave = vm::updateColor,
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

    HabitInputFields(
        modifier = modifier,
        uiState = uiState,
        vm = vm,
        navigateBack = navigateBack,
        updateIsOpenColorPickerState = {
            isOpenColorPicker = it
        }
    )
}

@Composable
private fun HabitInputFields(
    modifier: Modifier,
    uiState: HabitUiState,
    vm: HabitViewModel,
    navigateBack: () -> Unit,
    updateIsOpenColorPickerState: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        TextFieldTitle(
            uiState = uiState,
            updateTitle = vm::updateTitle
        )

        TextFieldDescription(
            uiState = uiState,
            updateDescription = vm::updateDescription
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

        HabitTypeRadioButtons(
            uiState = uiState,
            updateType = vm::updateType
        )

        TextFieldCount(
            uiState = uiState,
            updateCount = vm::updateCount
        )

        TextFieldPeriod(
            uiState = uiState,
            updatePeriod = vm::updatePeriod
        )

        ColorSelectItem(
            uiState = uiState,
            updateIsOpenColorPickerState = updateIsOpenColorPickerState
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        ButtonSave {
            vm.saveState(navigateBack)
        }
    }
}

@Composable
private fun ButtonSave(
    saveHabit: () -> Unit,
) {
    Button(
        onClick = saveHabit
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.button_save_habit),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TextFieldTitle(
    uiState: HabitUiState,
    updateTitle: (String) -> Unit
) {
    OutlineTextFieldMinimalistic(
        value = uiState.title,
        onValueChange = updateTitle,
        isError = uiState.isTitleError,
        label = R.string.title_habit
    )
}

@Composable
private fun TextFieldDescription(
    uiState: HabitUiState,
    updateDescription: (String) -> Unit
) {
    OutlineTextFieldMinimalistic(
        value = uiState.description,
        onValueChange = updateDescription,
        isError = uiState.isDescriptionError,
        label = R.string.description_habit,
        singleLine = false
    )
}

@Composable
private fun TextFieldCount(
    uiState: HabitUiState,
    updateCount: (String) -> Unit
) {
    OutlineTextFieldMinimalistic(
        value = uiState.count,
        onValueChange = updateCount,
        keyboardType = KeyboardType.Number,
        isError = uiState.isCountError,
        label = R.string.need_count
    )
}

@Composable
private fun TextFieldPeriod(
    uiState: HabitUiState,
    updatePeriod: (String) -> Unit
) {
    OutlineTextFieldMinimalistic(
        value = uiState.period,
        onValueChange = updatePeriod,
        imeAction = ImeAction.Done,
        isError = uiState.isPeriodError,
        label = R.string.period_input
    )
}

@Composable
private fun ColorSelectItem(
    uiState: HabitUiState,
    updateIsOpenColorPickerState: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                updateIsOpenColorPickerState(true)
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
}

@Composable
private fun ShowNotValidInputSnackbar(
    snackbarHostState: SnackbarHostState,
    updateStatus: (Boolean) -> Unit
) {
    val message = stringResource(R.string.error_message_input_habit_data)
    val okButton = stringResource(R.string.error_message_ok)

    LaunchedEffect(snackbarHostState) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = okButton,
            duration = SnackbarDuration.Short
        )

        when (snackbarResult) {
            SnackbarResult.Dismissed, SnackbarResult.ActionPerformed ->
                updateStatus(false)
        }
    }
}

@Composable
private fun HabitTypeRadioButtons(
    updateType: (Type) -> Unit,
    uiState: HabitUiState
) {
    Type.values().forEach { type ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    updateType(type)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = uiState.type == type,
                onClick = {
                    updateType(type)
                }
            )
            Text(text = stringResource(type.stringResRadioButton))
        }
    }
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