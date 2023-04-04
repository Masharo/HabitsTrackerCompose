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
) : ViewModel() {

    private val startSearch = ""
    private val startIsAsc = true

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = ColumnSort.ID.getHabits(
        habitRepository = habitRepository,
        search = startSearch,
        isAsc = startIsAsc
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
    )

    private val _uiState = MutableStateFlow(
        habits.value.toHabitListUiState(
            countPage = countPage,
            pages = pages,
            search = startSearch,
            columnSort = ColumnSort.ID,
            isAsc = startIsAsc
        )
    )

    init {
        habitsObserver()
    }

    fun searchUpdate(search: String) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                search = search
            )
        }
        habitListUpdate()
    }

    fun isAscUpdate(isAsc: Boolean) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                isAsc = isAsc
            )
        }
        habitListUpdate()
    }

    fun columnSortUpdate(columnSort: ColumnSort) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                columnSort = columnSort
            )
        }
        habitListUpdate()
    }

    private fun habitsObserver() {
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

    private fun habitListUpdate() {
        with(_uiState.value) {
            habits = columnSort.getHabits(
                habitRepository = habitRepository,
                search = search,
                isAsc = isAsc
            ).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            )
        }
        habitsObserver()
    }

    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

enum class ColumnSort {


    PRIORITY {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): Flow<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByPriority(
            title = search,
            isAsc = isAsc
        )

    },

    COUNT {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): Flow<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByCount(
            title = search,
            isAsc = isAsc
        )

    },

    ID {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): Flow<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderById(
            title = search,
            isAsc = isAsc
        )

    };

    abstract fun getHabits(
        habitRepository: HabitRepository,
       search: String,
       isAsc: Boolean
    ): Flow<List<Habit>>
}