package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import com.masharo.habitstrackercompose.model.HabitListUiState
import com.masharo.habitstrackercompose.model.toHabitListItemUiState
import com.masharo.habitstrackercompose.model.toHabitListUiState
import com.masharo.habitstrackercompose.usecase.GetHabitsListFromCacheUseCase
import com.masharo.habitstrackercompose.usecase.IncReadyCountHabitUseCase
import com.masharo.habitstrackercompose.usecase.UpdateLocalCacheHabitsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitListViewModel(
    private val getHabitsListFromCacheUseCase: GetHabitsListFromCacheUseCase,
    private val updateLocalCacheHabitsUseCase: UpdateLocalCacheHabitsUseCase,
    private val incReadyCountHabitUseCase: IncReadyCountHabitUseCase
) : ViewModel() {

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = getHabits(
            columnSortHabits = ColumnSortHabits.defaultValue(),
            typeSort = TypeSort.defaultValue(),
            search = START_SEARCH
        )
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
            columnSortHabits = ColumnSortHabits.defaultValue(),
            isAsc = TypeSort.defaultValue().getValue()
        )
    )

    init {
        updateLocalCacheHabits()
        updateHabits()
    }

    fun messageStatusToNone() {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                message = HabitMessage.NONE
            )
        }
    }

    fun updateLocalCacheHabits() = updateLocalCacheHabitsUseCase.execute()


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

    fun columnSortUpdate(columnSortHabits: ColumnSortHabits) {
        _uiState.update { stateCurrent ->
            stateCurrent.copy(
                columnSortHabits = columnSortHabits
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
        columnSortHabits: ColumnSortHabits,
        typeSort: TypeSort,
        search: String
    ): Flow<List<Habit>> = getHabitsListFromCacheUseCase.execute(
        search = search,
        isAsc = typeSort.getValue(),
        columnSortHabits.toColumnSort()
    )

    private fun habitListUpdate() {
        with(_uiState.value) {
            habits = getHabits(
                    columnSortHabits = columnSortHabits,
                    typeSort = typeSort,
                    search = search
                )
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = listOf()
                )
        }
        updateHabits()
    }

    fun incCountReady(habitId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            incReadyCountHabitUseCase.execute(habitId)
        }
    }

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

        private const val START_SEARCH = ""

    }
}