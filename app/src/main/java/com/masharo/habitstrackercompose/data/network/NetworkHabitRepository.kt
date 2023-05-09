package com.masharo.habitstrackercompose.data.network

interface NetworkHabitRepository {

    fun downloadHabits()

    fun updateHabit()

    fun saveHabit()

}