package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.IncAnswer
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class IncHabitUseCase(
    private val dbRepository: DBHabitRepository,
    private val networkRepository: NetworkHabitRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habit: Habit): IncAnswer {
        val newHabit = habit.copy(countReady = habit.countReady + 1)

        withContext(dispatcher) {
            dbRepository.update(newHabit)
            networkRepository.updateHabit(habit.id)
        }

        return if ()
    }

}