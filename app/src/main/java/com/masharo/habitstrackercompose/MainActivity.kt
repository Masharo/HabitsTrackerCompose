package com.masharo.habitstrackercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.masharo.habitstrackercompose.ui.screen.HabitsTrackerApp
import com.masharo.habitstrackercompose.ui.theme.HabitsTrackerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitsTrackerComposeTheme {
                HabitsTrackerApp()
            }
        }
    }
}