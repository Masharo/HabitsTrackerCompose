package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habits: MutableStateFlow<List<Habit>>
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

//    fun updatePage(numberPage: Int) {
//        val pageInput = Page.values()[numberPage]
//        if (pageInput != page) {
//            _uiState.update { habitsCurrent ->
//                habitsCurrent.copy(
//                    habits = habits.value.filter { pageInput.filter(it) }
//                        .map { it.toHabitListItemUiState() }
//                )
//            }
//            page = pageInput
//        }
//    }
}

enum class Page(
    @StringRes val title: Int
) {
    POSITIVE_HABIT_LIST(
        title = R.string.page_positive
    ) {
        override fun filter(
            habit: Habit
        ) = habit.type == Type.POSITIVE
    },
    NEGATIVE_HABIT_LIST(
        title = R.string.page_negative
    ) {
        override fun filter(
            habit: Habit
        ) = habit.type == Type.NEGATIVE
    };

    abstract fun filter(
        habit: Habit
    ): Boolean
}