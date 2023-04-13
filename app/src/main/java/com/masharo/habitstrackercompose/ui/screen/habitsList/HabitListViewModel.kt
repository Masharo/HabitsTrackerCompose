package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.data.HabitRepository
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.toHabitListItemUiState
import com.masharo.habitstrackercompose.model.toHabitListUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = getHabits(
        columnSort = ColumnSort.defaultValue(),
        typeSort = TypeSort.defaultValue(),
        search = START_SEARCH
    )
    .asFlow()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
    )

    private val _uiState = MutableStateFlow(
        habits.value.toHabitListUiState(
            countPage = countPage,
            pages = pages,
            search = START_SEARCH,
            columnSort = ColumnSort.defaultValue(),
            isAsc = TypeSort.defaultValue().getValue()
        )
    )

    init {
        updateHabits()
    }

    fun searchUpdate(search: String) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                search = search
            )
        }
        habitListUpdate()
    }

    fun typeSortUpdate(typeSort: TypeSort) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                typeSort = typeSort
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

    private fun updateHabits() {
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

    private fun getHabits(
        columnSort: ColumnSort,
        typeSort: TypeSort,
        search: String
    ): LiveData<List<Habit>> =
        when (columnSort) {
            ColumnSort.PRIORITY -> habitRepository.getAllHabitsLikeTitleOrderByPriority(
                title = search,
                isAsc = typeSort.getValue()
            )
            ColumnSort.ID -> habitRepository.getAllHabitsLikeTitleOrderById(
                title = search,
                isAsc = typeSort.getValue()
            )
            ColumnSort.COUNT -> habitRepository.getAllHabitsLikeTitleOrderByCount(
                title = search,
                isAsc = typeSort.getValue()
            )
        }

    private fun habitListUpdate() {
        with(_uiState.value) {
            habits = getHabits(
                    columnSort = columnSort,
                    typeSort = typeSort,
                    search = search
                )
                .asFlow()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = listOf()
                )
        }
        updateHabits()
    }

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        private const val START_SEARCH = ""

    }
}