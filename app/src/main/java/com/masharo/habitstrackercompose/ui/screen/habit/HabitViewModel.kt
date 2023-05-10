package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.data.db.DBHabitRepository
import com.masharo.habitstrackercompose.data.model.toHabitUiState
import com.masharo.habitstrackercompose.data.network.NetworkHabitRepository
import com.masharo.habitstrackercompose.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitViewModel(
    private val idHabit: Long? = null,
    private val dbHabitRepository: DBHabitRepository,
    private val networkHabitRepository: NetworkHabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        value = HabitUiState()
    )

    private var uid = ""

    init {
        idHabit?.let { id ->
            viewModelScope.launch {
                dbHabitRepository.getHabitById(id)?.let { habit ->
                    _uiState.update {
                        habit.toHabitUiState()
                    }
                    uid = habit.uid
                }
            }
        }
    }

    val uiState = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                isTitleError = false,
                isError = false
            )
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = description,
                isDescriptionError = false,
                isError = false
            )
        }
    }

    fun updateCount(count: String) {
        if (validateCount(count)) return

        _uiState.update { currentState ->
            currentState.copy(
                count = count,
                isCountError = false,
                isError = false
            )
        }
    }

    fun updateCountReady(countReady: String) {
        if (validateCount(countReady)) return

        countReady.toIntOrNull()?.let {
            _uiState.update { currentState ->
                currentState.copy(
                    count = countReady,
                    isCountReadyError = false,
                    isError = false
                )
            }
        }
    }

    //Фильтруем ситуации ввода отрицательных чисел и не числовых значений
    private fun validateCount(count: String) =
        count.isNotEmpty() && (
            count.toIntOrNull() == null ||
            count.toInt() < 0 ||
            count.matches(Regex("""^0.*"""))
        )

    fun updatePeriod(period: Period) {
        _uiState.update { currentState ->
            currentState.copy(
                period = period,
                isError = false
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

    fun updateIsError(value: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isError = value
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

    fun saveState() {
        with(uiState.value) {

            val isTitleErrorCurrent = title.isEmpty()
            val isDescriptionErrorCurrent = description.isEmpty()
            val isCountErrorCurrent = count.isEmpty() || count.toInt() < 0
            val isCountReadyErrorCurrent = countReady.isEmpty() || countReady.toInt() < 0

            if (isTitleErrorCurrent ||
                isDescriptionErrorCurrent ||
                isCountErrorCurrent ||
                isCountReadyErrorCurrent
            ) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isTitleError = isTitleErrorCurrent,
                        isDescriptionError = isDescriptionErrorCurrent,
                        isCountError = isCountErrorCurrent,
                        isCountReadyError = isCountReadyErrorCurrent,
                        isError = true
                    )
                }
            } else {
                viewModelScope.launch {
                    idHabit?.let { id ->
                        dbHabitRepository.update(this@with.toHabit(id, uid))
                        networkHabitRepository.updateHabit(id)
                    } ?: run {
                        val id = dbHabitRepository.insert(this@with.toHabit())
                        networkHabitRepository.createHabit(id)
                    }
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        isNavigateToHabitsList = true
                    )
                }
            }
        }
    }
}