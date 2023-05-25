package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habitstrackercompose.db.DBHabitRepository
import com.masharo.habitstrackercompose.network.NetworkHabitRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HabitListViewModelFactory @Inject constructor(
    private val dbHabitRepository: com.masharo.habitstrackercompose.db.DBHabitRepository,
    private val networkHabitRepository: com.masharo.habitstrackercompose.network.NetworkHabitRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        HabitListViewModel(
            dbHabitRepository = dbHabitRepository,
            networkHabitRepository = networkHabitRepository
        ) as T

}