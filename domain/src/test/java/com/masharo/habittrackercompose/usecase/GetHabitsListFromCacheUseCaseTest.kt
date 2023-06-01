package com.masharo.habits.domain.usecase

import com.masharo.habitstrackercompose.model.ColumnSort
import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.HabitType
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.usecase.GetHabitsListFromCacheUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetHabitsListFromCacheUseCaseTest{

    private val dbRepository = mock<DBHabitRepository>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
    }

    @Test
    fun shouldReturnCorrectData() {

        val testHabit = flowOf(
            listOf(
                Habit(
                    id = 10,
                    uid = "",
                    title = "Test1",
                    description = "Test2",
                    priority = 1,
                    type = HabitType.POSITIVE,
                    count = 11,
                    period = 2,
                    countReady = 5,
                    color = -1026
                ),

                Habit(
                    id = 1,
                    uid = "null",
                    title = "Test3",
                    description = "Test4",
                    priority = 0,
                    type = HabitType.POSITIVE,
                    count = 12,
                    period = 2,
                    countReady = 5,
                    color = -3056
                )
            )

        )

        Mockito.`when`(dbRepository.getAllHabitsLikeTitleOrderById("", true)).thenReturn(testHabit)

        val useCase = GetHabitsListFromCacheUseCase(dbHabitRepository = dbRepository)

        val expected = runBlocking { useCase.execute(
            search = "",
            isAsc = true,
            columnSort = ColumnSort.ID,

        ).first() }
        val actual = listOf(
            Habit(
                id = 10,
                uid = "",
                title = "Test1",
                description = "Test2",
                priority = 1,
                type = HabitType.POSITIVE,
                count = 11,
                period = 2,
                countReady = 5,
                color = -1026
            ),

            Habit(
                id = 1,
                uid = "null",
                title = "Test3",
                description = "Test4",
                priority = 0,
                type = HabitType.POSITIVE,
                count = 12,
                period = 2,
                countReady = 5,
                color = -3056
            )
        )

        Assert.assertEquals(expected, actual)
    }

}