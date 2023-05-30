package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.repository.DBHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetHabitFromCacheUseCase(
    private val dbRepository: DBHabitRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Long) = withContext(dispatcher) {
        return@withContext dbRepository.getHabitById(id)
    }

}