package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habitstrackercompose.R
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
    private val typeSort = TypeSort.ASC

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = ColumnSort.ID.getHabits(
        habitRepository = habitRepository,
        search = startSearch,
        isAsc = typeSort.getValue()
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
            isAsc = typeSort.getValue()
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
                isAsc = typeSort.getValue()
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

enum class ColumnSort(
   @StringRes val title: Int,
   @StringRes val selectedTitle: Int
) {

    PRIORITY(
        title = R.string.column_priority_variant,
        selectedTitle = R.string.column_priority_selected
    ) {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): Flow<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByPriority(
            title = search,
            isAsc = isAsc
        )

    },

    COUNT(
        title = R.string.column_count_variant,
        selectedTitle = R.string.column_count_selected
    ) {

        override fun getHabits(
            habitRepository: HabitRepository,
            search: String,
            isAsc: Boolean
        ): Flow<List<Habit>> = habitRepository.getAllHabitsLikeTitleOrderByCount(
            title = search,
            isAsc = isAsc
        )

    },

    ID(
        title = R.string.column_id_variant,
        selectedTitle = R.string.column_id_selected
    ) {

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

enum class TypeSort(
    @StringRes title: Int,
    @StringRes selectedTitle: Int
) {
    ASC(
        title = R.string.type_sort_asc_variant,
        selectedTitle = R.string.type_sort_asc_selected
    ) {
        override fun getValue() = true
    },
    DESC(
        title = R.string.type_sort_desc_variant,
        selectedTitle = R.string.type_sort_desc_selected
    ) {
        override fun getValue() = false
    };

    abstract fun getValue(): Boolean
}