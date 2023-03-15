package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.toHabitListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitListViewModel(
    private val habits: List<Habit>
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        habits.toHabitListUiState()
    )

    val uiState = _uiState.asStateFlow()



}