package com.masharo.habitstrackercompose.ui.screen.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habitstrackercompose.data.db.DBHabitRepository
import com.masharo.habitstrackercompose.data.network.NetworkHabitRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("UNCHECKED_CAST")
class HabitViewModelFactory @AssistedInject constructor(
    @Assisted("idHabit") private val idHabit: Long? = null,
    private val dbHabitRepository: DBHabitRepository,
    private val networkHabitRepository: NetworkHabitRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        HabitViewModel(
            idHabit = idHabit,
            dbHabitRepository = dbHabitRepository,
            networkHabitRepository = networkHabitRepository
        ) as T

    @AssistedFactory
    interface Params {
        fun create(@Assisted("idHabit") idHabit: Long? = null): HabitViewModel
    }

}