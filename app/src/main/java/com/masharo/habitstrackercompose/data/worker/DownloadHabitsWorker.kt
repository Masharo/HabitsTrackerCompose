package com.masharo.habitstrackercompose.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habitstrackercompose.data.db.HabitDao
import com.masharo.habitstrackercompose.data.model.toHabitsDB
import com.masharo.habitstrackercompose.data.network.HabitApiService
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
                //Можно сравнить даты максимального из локальной бд и запуска
                val habits = api.getHabits().toHabitsDB()
                db.deleteAll()
                db.insertHabits(*habits.toTypedArray())
                Result.success()
            } catch (ex: IOException) {
                Result.retry()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }

}