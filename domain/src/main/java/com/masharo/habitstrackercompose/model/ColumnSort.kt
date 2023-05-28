package com.masharo.habitstrackercompose.model

enum class ColumnSort() {

    PRIORITY,

    COUNT,

    ID;

    companion object {

        fun defaultValue() = ID

    }
}