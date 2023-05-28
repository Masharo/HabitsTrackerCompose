package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddHabitUseCase(
    private val dbRepository: DBHabitRepository,
    private val networkRepository: NetworkHabitRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habit: Habit) = withContext(dispatcher) {
        val id = dbRepository.insert(habit)
        networkRepository.createHabit(id)
    }

}