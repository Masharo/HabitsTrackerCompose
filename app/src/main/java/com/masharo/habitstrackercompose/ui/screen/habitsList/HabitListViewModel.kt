package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.db.DBHabitRepository
import com.masharo.habitstrackercompose.model.HabitDB
import com.masharo.habitstrackercompose.model.toHabitListItemUiState
import com.masharo.habitstrackercompose.model.toHabitListUiState
import com.masharo.habitstrackercompose.network.NetworkHabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitListViewModel @Inject constructor(
    private val dbHabitRepository: com.masharo.habitstrackercompose.db.DBHabitRepository,
    private val networkHabitRepository: com.masharo.habitstrackercompose.network.NetworkHabitRepository
) : ViewModel() {

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = getHabits(
            columnSort = ColumnSort.defaultValue(),
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
            columnSort = ColumnSort.defaultValue(),
            isAsc = TypeSort.defaultValue().getValue()
        )
    )

    init {
        networkUpdateHabits()
        updateHabits()
    }

    fun networkUpdateHabits() = networkHabitRepository.downloadHabits()


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
    ): Flow<List<com.masharo.habitstrackercompose.model.HabitDB>> =
        when (columnSort) {
            ColumnSort.PRIORITY -> dbHabitRepository.getAllHabitsLikeTitleOrderByPriority(
                title = search,
                isAsc = typeSort.getValue()
            )
            ColumnSort.ID -> dbHabitRepository.getAllHabitsLikeTitleOrderById(
                title = search,
                isAsc = typeSort.getValue()
            )
            ColumnSort.COUNT -> dbHabitRepository.getAllHabitsLikeTitleOrderByCount(
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