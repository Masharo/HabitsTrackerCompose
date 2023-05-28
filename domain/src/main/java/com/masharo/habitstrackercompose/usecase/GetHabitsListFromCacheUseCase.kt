package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.model.ColumnSort
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import kotlinx.coroutines.flow.Flow

class GetHabitsListFromCacheUseCase(
    private val dbHabitRepository: DBHabitRepository
) {

    fun execute(
        search: String,
        isAsc: Boolean,
        columnSort: ColumnSort
    ): Flow<List<Habit>> = when (columnSort) {
        ColumnSort.PRIORITY -> dbHabitRepository.getAllHabitsLikeTitleOrderByPriority(
            title = search,
            isAsc = isAsc
        )
        ColumnSort.ID -> dbHabitRepository.getAllHabitsLikeTitleOrderById(
            title = search,
            isAsc = isAsc
        )
        ColumnSort.COUNT -> dbHabitRepository.getAllHabitsLikeTitleOrderByCount(
            title = search,
            isAsc = isAsc
        )
    }

}