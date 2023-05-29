package com.masharo.habitstrackercompose.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habitstrackercompose.db.HabitDao
import com.masharo.habitstrackercompose.model.toHabitsDB
import com.masharo.habitstrackercompose.network.HabitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DownloadHabitsWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val api: HabitApiService by inject()
    private val db: HabitDao by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getHabits()
                response.errorBody()?.let {
                    return@withContext Result.retry()
                }
                response.body()?.toHabitsDB()?.let { habits ->
                    db.deleteAll()
                    db.insertHabits(*habits.toTypedArray())
                    return@withContext Result.success()
                }
                Result.failure()
            } catch (ex: IOException) {
                Result.retry()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }

}