package com.masharo.habitstrackercompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masharo.habitstrackercompose.data.model.HabitDB

@Database(entities = [HabitDB::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

}