package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.data.HabitRepository
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.Page
import com.masharo.habitstrackercompose.model.toHabitListItemUiState
import com.masharo.habitstrackercompose.model.toHabitListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitRepository: HabitRepository
//    private val habits: MutableStateFlow<List<Habit>>
) : ViewModel() {

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private val habits = habitRepository.getAllHabits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    private val _uiState = MutableStateFlow(
        habits.value.toHabitListUiState(
            countPage = countPage,
            pages = pages
        )
    )

    init {
        viewModelScope.launch {
            habits.collect {
                _uiState.update { habitsCurrent ->
                    habitsCurrent.copy(
                        habitsPositive = it.toHabitListItemUiState(
                            filter = Page.POSITIVE_HABIT_LIST::filter
                        ),
                        habitsNegative = it.toHabitListItemUiState(
                            filter = Page.NEGATIVE_HABIT_LIST::filter
                        )
                    )
                }
            }

        }
    }

    val uiState = _uiState.asStateFlow()

    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

