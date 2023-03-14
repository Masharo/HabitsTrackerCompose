package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.masharo.habitstrackercompose.data.habits
import com.masharo.habitstrackercompose.model.HabitUiState
import com.masharo.habitstrackercompose.model.Priority
import com.masharo.habitstrackercompose.model.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HabitViewModel(
    private val idHabit: Int? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        value = idHabit?.let { id -> habits[id] }
                ?:
                HabitUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                isTitleError = false
            )
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                isDescriptionError = false
            )
        }
    }

    fun updateCount(count: String) {
        if (validateCount(count)) return

        _uiState.update { currentState ->
            currentState.copy(
                count = count,
                isCountError = false
            )
        }
    }

    fun updateCountReady(countReady: String) {
        if (validateCount(countReady)) return

        countReady.toIntOrNull()?.let {
            _uiState.update { currentState ->
                currentState.copy(
                    count = countReady,
                    isCountReadyError = false
                )
            }
        }
    }

    //Фильтруем ситуации ввода отрицательных чисел и не числовых значений
    private fun validateCount(count: String) =
        count.isNotEmpty() && (count.toIntOrNull() == null || count.toInt() < 0)

    fun updatePeriod(period: String) {
        _uiState.update { currentState ->
            currentState.copy(
                period = period,
                isPeriodError = false
            )
        }
    }

    fun updateType(type: Type) {
        _uiState.update { currentState ->
            currentState.copy(
                type = type
            )
        }
    }

    fun updatePriority(priority: Priority) {
        _uiState.update { currentState ->
            currentState.copy(
                priority = priority
            )
        }
    }

    fun updateColor(color: Color?) {
        _uiState.update { currentState ->
            currentState.copy(
                color = color
            )
        }
    }

    fun saveState(
        navigateBack: () -> Unit
    ) {
        with(uiState.value) {

            val isTitleErrorCurrent = title.isEmpty()
            val isDescriptionErrorCurrent = description.isEmpty()
            val isCountErrorCurrent = count.isEmpty() || count.toInt() < 0
            val isCountReadyErrorCurrent = countReady.isEmpty() || countReady.toInt() < 0
            val isPeriodErrorCurrent = period.isEmpty()

            if (isTitleErrorCurrent ||
                isDescriptionErrorCurrent ||
                isCountErrorCurrent ||
                isCountReadyErrorCurrent ||
                isPeriodErrorCurrent
            ) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isTitleError = isTitleErrorCurrent,
                        isDescriptionError = isDescriptionErrorCurrent,
                        isCountError = isCountErrorCurrent,
                        isCountReadyError = isCountReadyErrorCurrent,
                        isPeriodError = isPeriodErrorCurrent
                    )
                }
            } else {
                idHabit?.let { id ->
                    habits[id] = this
                } ?: habits.add(this)

                navigateBack()
            }
        }
    }
}