package com.masharo.habitstrackercompose.usecase

import com.masharo.habitstrackercompose.repository.NetworkHabitRepository

class UpdateLocalCacheHabitsUseCase(
    private val networkRepository: NetworkHabitRepository
) {

    fun execute() = networkRepository.downloadHabits()

}