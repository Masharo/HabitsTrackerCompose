package com.masharo.habitstrackercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.masharo.habitstrackercompose.ui.screen.HabitsTrackerApp
import com.masharo.habitstrackercompose.ui.theme.HabitsTrackerComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var isVisibleSplashScreen = true
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            isVisibleSplashScreen
        }
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_HabitsTrackerCompose)
        setContent {
            HabitsTrackerComposeTheme {
                HabitsTrackerApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            isVisibleSplashScreen = false
        }
    }
}