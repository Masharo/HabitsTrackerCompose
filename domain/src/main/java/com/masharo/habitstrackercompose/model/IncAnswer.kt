package com.masharo.habitstrackercompose.model

sealed class IncAnswer

object GoodHabitMore : IncAnswer()
class GoodHabitLess(val count: Int) : IncAnswer()
object BadHabitMore : IncAnswer()
class BadHabitLess(val count: Int) : IncAnswer()