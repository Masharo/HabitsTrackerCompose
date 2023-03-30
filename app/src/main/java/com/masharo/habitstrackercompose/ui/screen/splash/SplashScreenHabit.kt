package com.masharo.habitstrackercompose.ui.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.masharo.habitstrackercompose.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreenHabit(
    modifier: Modifier = Modifier,
    navigateToNextScreen: () -> Unit
) {
    var isStartAnimate by remember { mutableStateOf(false) }
    val animateAlpha = animateFloatAsState(
        targetValue = if (isStartAnimate) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(
        key1 = true
    ) {
        isStartAnimate = true
        delay(4000)
        navigateToNextScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .alpha(animateAlpha.value),
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
    SplashScreenHabit(navigateToNextScreen = {})
}