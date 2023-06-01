package com.masharo.habits.domain.usecase


import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.HabitType
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.usecase.GetHabitFromCacheUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


class GetHabitFromCacheUseCaseTest {

    private val dbRepository = mock<DBHabitRepository>()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
    }

    @Test
    fun shouldReturnCorrectData() {

        val testHabit = Habit(
            id = 10,
            uid = "",
            title = "Test1",
            description = "Test2",
            priority = 1,
            type = HabitType.POSITIVE,
            count = 11,
            period = 2,
            countReady = 5,
            color = null
        )

        dbRepository.stub {
            onBlocking {
                getHabitById(10)
            }.thenReturn(testHabit)
        }


        val useCase = GetHabitFromCacheUseCase(dbRepository = dbRepository, dispatcher)

        val expected = runBlocking { useCase.execute(10) }
        val actual = Habit(
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
        )

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnCorrectDataNull() {

        val testHabit = null

        dbRepository.stub {
            onBlocking {
                getHabitById(100)
            }.thenReturn(testHabit)
        }


        val useCase = GetHabitFromCacheUseCase(dbRepository = dbRepository, dispatcher)

        val expected = runBlocking { useCase.execute(100) }
        val actual = null

        assertEquals(expected, actual)
    }

}