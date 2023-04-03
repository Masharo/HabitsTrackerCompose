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

    private val countPage = Page.values().size
    private val pages = Page.values().map { it.title }
    private var habits = habitRepository.getAllHabitsLikeTitleOrderById("", true)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    private val _uiState = MutableStateFlow(
        habits.value.toHabitListUiState(
            countPage = countPage,
            pages = pages,
            habitSort = Id(
                like = "",
                isAsc = true
            )
        )
    )

    init {
        habitsObserver()
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

    fun habitSortUpdate(habitSort: HabitSort) {
        habits = habitSort.getHabits(habitRepository)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = listOf()
            )
    }

    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

sealed class HabitSort(internal val like: String, internal val isAsc: Boolean) {

    abstract fun getHabits(habitRepository: HabitRepository): Flow<List<Habit>>

}
class Priority(like: String, isAsc: Boolean) : HabitSort(like, isAsc) {

    override fun getHabits(habitRepository: HabitRepository): Flow<List<Habit>> =
        habitRepository.getAllHabitsLikeTitleOrderByPriority(like, isAsc)

}
class Count(like: String, isAsc: Boolean) : HabitSort(like, isAsc) {

    override fun getHabits(habitRepository: HabitRepository): Flow<List<Habit>> =
        habitRepository.getAllHabitsLikeTitleOrderByCount(like, isAsc)

}

class Id(like: String, isAsc: Boolean) : HabitSort(like, isAsc) {

    override fun getHabits(habitRepository: HabitRepository): Flow<List<Habit>> =
        habitRepository.getAllHabitsLikeTitleOrderById(like, isAsc)

}