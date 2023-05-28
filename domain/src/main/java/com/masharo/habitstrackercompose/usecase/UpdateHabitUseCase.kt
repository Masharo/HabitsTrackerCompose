package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateHabitUseCase(
    private val dbRepository: DBHabitRepository,
    private val networkRepository: NetworkHabitRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habit: Habit) = withContext(dispatcher) {
        dbRepository.update(habit)
        networkRepository.updateHabit(habit.id)
    }

}