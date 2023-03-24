package com.masharo.habitstrackercompose.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.ui.theme.BGSplashScreen
import com.masharo.habitstrackercompose.ui.theme.BGSplashScreenDark

@Composable
fun SplashScreenHabit(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (isSystemInDarkTheme()) BGSplashScreenDark else BGSplashScreen
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.splash_logo),
            contentDescription = null
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewSplashScreenHabit() {
    SplashScreenHabit()
}