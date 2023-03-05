package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitUiState
import com.masharo.habitstrackercompose.model.Priority
import com.masharo.habitstrackercompose.model.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HabitViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HabitUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title
            )
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description
            )
        }
    }

    fun updateCount(count: String) {
        if (count.isNotEmpty() && count.toIntOrNull() == null) return

        _uiState.update { currentState ->
            currentState.copy(
                count = count
            )
        }
    }

    fun updateCountReady(countReady: String) {
        if (countReady.isNotEmpty() && countReady.toIntOrNull() == null) return

        countReady.toIntOrNull()?.let {
            _uiState.update { currentState ->
                currentState.copy(
                    count = countReady
                )
            }
        }
    }

    fun updatePeriod(period: String) {
        _uiState.update { currentState ->
            currentState.copy(
                period = period
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
}