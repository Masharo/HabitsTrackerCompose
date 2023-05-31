package com.masharo.habitstrackercompose.ui.screen.habitsList

import com.masharo.habitstrackercompose.model.BadHabitLess
import com.masharo.habitstrackercompose.model.BadHabitMore
import com.masharo.habitstrackercompose.model.GoodHabitLess
import com.masharo.habitstrackercompose.model.GoodHabitMore
import com.masharo.habitstrackercompose.model.IncAnswer

sealed class HabitMessage

object None : HabitMessage()
object GoodHabitMoreUi : HabitMessage()
class GoodHabitLessUi(val count: Int) : HabitMessage()
object BadHabitMoreUi : HabitMessage()
class BadHabitLessUi(val count: Int) : HabitMessage()

fun IncAnswer.toHabitMessage() = when (this) {
    is BadHabitLess -> BadHabitLessUi(count)
    is BadHabitMore -> BadHabitMoreUi
    is GoodHabitLess -> GoodHabitLessUi(count)
    is GoodHabitMore -> GoodHabitMoreUi
}