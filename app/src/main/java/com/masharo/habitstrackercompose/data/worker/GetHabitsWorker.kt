package com.masharo.habitstrackercompose.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habitstrackercompose.data.network.HabitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetHabitsWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val habitApiService: HabitApiService by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            habitApiService.getHabits()
            Result.success()
        }
    }

}