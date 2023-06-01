package com.masharo.habittrackercompose.usecase

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.HabitType
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import com.masharo.habitstrackercompose.usecase.UpdateHabitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class UpdateHabitUseCaseTest {

    private val dbRepository = mock<DBHabitRepository>()
    private val remoteRepository = mock<NetworkHabitRepository>()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
        Mockito.reset(remoteRepository)
    }

    @Test
    fun shouldReturnTrue() {

        val testParamHabit = Habit(
            id = 0,
            uid = "",
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = HabitType.POSITIVE,
            count = 12,
            period = 2,
            countReady = 5,
            color = -3056
        )

        dbRepository.stub {
            onBlocking {
                update(testParamHabit)
            }.doReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                updateHabit(testParamHabit.id)
            }.doReturn(Unit)
        }

        val useCase = UpdateHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = true

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnExceptionInDb() {

        val testParamHabit = Habit(
            id = 0,
            uid = "",
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = HabitType.POSITIVE,
            count = 12,
            period = 2,
            countReady = 5,
            color = -3056
        )

        dbRepository.stub {
            onBlocking {
                update(testParamHabit)
            }.thenThrow(RuntimeException())
        }

        val useCase = UpdateHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = false

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnExceptionInRemote() {

        val testParamHabit = Habit(
            id = 0,
            uid = "",
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = HabitType.POSITIVE,
            count = 12,
            period = 2,
            countReady = 5,
            color = -3056
        )

        dbRepository.stub {
            onBlocking {
                update(testParamHabit)
            }.thenReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                updateHabit(testParamHabit.id)
            }.thenThrow(RuntimeException())
        }

        val useCase = UpdateHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = false

        Assert.assertEquals(expected, actual)

    }

}