package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.toHabitListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habits: MutableStateFlow<List<Habit>>
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        habits.value.toHabitListUiState()
    )

    init {
        viewModelScope.launch {
            habits.collect {
                _uiState.update { habitsCurrent ->
                    habitsCurrent.copy(
                        habits = it.toHabitListUiState().habits
                    )
                }
            }

        }
    }

    val uiState = _uiState.asStateFlow()

}