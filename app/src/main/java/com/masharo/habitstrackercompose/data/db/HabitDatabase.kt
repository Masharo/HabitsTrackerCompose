package com.masharo.habitstrackercompose.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.masharo.habitstrackercompose.data.model.HabitDB

@Database(entities = [HabitDB::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room
                    .databaseBuilder(
                        context = context,
                        klass = HabitDatabase::class.java,
                        name = "habit_database"
                    )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }

    }

}