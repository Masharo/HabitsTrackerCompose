package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.toHabitListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habits: MutableStateFlow<MutableList<Habit>>
) : ViewModel() {

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }

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
                        habits = it.toHabitListUiState(
                            countPage = countPage,
                            pages = pages
                        ).habits
                    )
                }
            }

        }
    }

    val uiState = _uiState.asStateFlow()

}

private enum class Page(
    @StringRes val title: Int
) {
    POSITIVE_HABIT_LIST(
        title = R.string.page_positive
    ),
    NEGATIVE_HABIT_LIST(
        title = R.string.page_negative
    )
}