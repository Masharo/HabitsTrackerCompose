package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.model.HabitType
import com.masharo.habitstrackercompose.model.IncAnswer
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class IncReadyCountHabitUseCase(
    private val dbRepository: DBHabitRepository,
    private val networkRepository: NetworkHabitRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Long): IncAnswer {
        val habit = dbRepository.getHabitById(id) ?: throw RuntimeException("Привычка с id: $id не найдена")
        val newHabit = habit.copy(countReady = habit.countReady + 1)

        withContext(dispatcher) {
            dbRepository.update(newHabit)
            networkRepository.incCountReadyHabit(newHabit.uid)
        }

        return  if (newHabit.type == HabitType.POSITIVE) {
                    if (newHabit.count > newHabit.countReady) IncAnswer.GOOD_HABIT_LESS
                    else IncAnswer.GOOD_HABIT_MORE
                } else {
                    if (newHabit.count > newHabit.countReady) IncAnswer.BAD_HABIT_LESS
                    else IncAnswer.BAD_HABIT_MORE
                }
    }

}