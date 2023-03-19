package com.masharo.habitstrackercompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.navigate.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsTrackerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = habitNavigateState(backStackEntry?.destination?.route)
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(DrawerValue.Closed) //optional

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 14.dp
                            ),
                        text = "Menu",//TODO("Вынести в ресурсы")
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondary
                            )
                            .height(2.dp)
                            .fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )
                    TextButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Text(
                            text = "Домашний экран"//TODO("Вынести в ресурсы")
                        )
                    }
                    TextButton(
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Text(
                            text = "О прилажении"//TODO("Вынести в ресурсы")
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FabHabit(
                    currentScreen = currentScreen,
                    onClick = {
                        navController.navigate(HabitNavigateState.AddNewHabit.name)
                    }
                )
            },
            snackbarHost = {
                SnackbarHostHabit(
                    snackbarHostState = snackbarHostState
                )
            },
            topBar = {
                HabitsTrackerAppBar(
                    currentScreen = currentScreen
                )
            }
        ) { contentPadding ->
            NavHost(
                navController = navController,
                startDestination = HabitNavigateState.Start.name,
                modifier = modifier.padding(contentPadding)
            ) {

                navigateToStartScreen(
                    navController = navController
                )

                navigateToUpdateHabitScreen(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )

                navigateToAddNewHabit(
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )

            }
        }
    }
}

@Composable
fun FabHabit(
    currentScreen: HabitNavigateState,
    onClick: () -> Unit
) {
    if (currentScreen.isNeedFabAddHabit) {
        FloatingActionButton(
            onClick = onClick
        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = stringResource(R.string.create_habit)
            )
        }
    }
}

@Composable
fun SnackbarHostHabit(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(snackbarHostState) { data ->
        Snackbar(
            modifier = modifier
                .padding(15.dp)
                .height(60.dp),
            dismissAction = {
                TextButton(
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp
                        )
                        .fillMaxHeight(),
                    onClick = {
                        data.dismiss()
                    }
                ) {
                    Text(
                        text = data.visuals.actionLabel ?: ""
                    )
                }
            }
        ) {
            Text(data.visuals.message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsTrackerAppBar(
    modifier: Modifier = Modifier,
    currentScreen: HabitNavigateState
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(currentScreen.screenTitle)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun HabitsTrackerPreview() {
    HabitsTrackerApp()
}