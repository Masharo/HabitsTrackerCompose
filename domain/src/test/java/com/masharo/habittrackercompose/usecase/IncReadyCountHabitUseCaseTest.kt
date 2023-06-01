package com.masharo.habits.domain.usecase

import com.masharo.habitstrackercompose.model.Habit
import com.masharo.habitstrackercompose.model.HabitType
import com.masharo.habitstrackercompose.repository.DBHabitRepository
import com.masharo.habitstrackercompose.repository.NetworkHabitRepository
import com.masharo.habitstrackercompose.usecase.IncReadyCountHabitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.*

class IncReadyCountHabitUseCaseTest {

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

        val testParamId = 10L
        val testParamHabit = Habit(
            id = 10,
            uid = "1234",
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
                dbRepository.getHabitById(testParamId)
            }.doReturn(testParamHabit)

            onBlocking {
                dbRepository.update(testParamHabit)
            }.doReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                incCountReadyHabit(testParamHabit.uid)
            }.doReturn(Unit)
        }

        val useCase = IncReadyCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamId) }
        val actual = true

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnFalseHabitNotInDb() {

        val testParamId = 10L

        dbRepository.stub {
            onBlocking {
                dbRepository.getHabitById(testParamId)
            }.doReturn(null)
        }

        val useCase = IncReadyCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamId) }
        val actual = false

        Assert.assertEquals(expected, actual)

        runBlocking {
            verify(dbRepository, never()).update(any())
            verify(remoteRepository, never()).incCountReadyHabit(any())
        }

    }

    @Test
    fun shouldReturnCount() {

        val testParamId = 10L
        val testParamHabit = Habit(
            id = 10,
            uid = "6878",
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = HabitType.POSITIVE,
            count = 12,
            period = 2,
            countReady = 5,
            color = -3056
        )

        val testParamHabitIncCountDone = Habit(
            id = 10,
            uid = "",
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = HabitType.POSITIVE,
            count = 12,
            period = 2,
            countReady = 6,
            color = -3056
        )

        dbRepository.stub {
            onBlocking {
                dbRepository.getHabitById(testParamId)
            }.doReturn(testParamHabit)

            onBlocking {
                dbRepository.update(testParamHabit)
            }.doReturn(Unit)
        }

        val useCase = IncReadyCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        runBlocking { useCase.execute(testParamId) }

        runBlocking {
            verify(dbRepository, times(1)).update(testParamHabitIncCountDone)
        }
    }

}