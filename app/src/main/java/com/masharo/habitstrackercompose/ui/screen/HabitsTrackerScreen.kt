package com.masharo.habitstrackercompose.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModelFactory
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitsListScreen
import kotlinx.coroutines.launch

const val ID_HABIT_PARAM_NAME = "idHabit"

enum class HabitsTrackerScreen(
    @StringRes val screenTitle: Int,
    val isNeedFabAddHabit: Boolean
) {
    Start(
        screenTitle = R.string.app_bar_name_screen_list_habits,
        isNeedFabAddHabit = true
    ),
    AddNewHabit(
        screenTitle = R.string.app_bar_name_screen_new_habit_add,
        isNeedFabAddHabit = false
    ),
    UpdateHabit(
        screenTitle = R.string.app_bar_name_screen_habit_update,
        isNeedFabAddHabit = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsTrackerApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route?.replace(Regex("""/.*"""), "")

    val currentScreen = HabitsTrackerScreen.valueOf(
        value = route
                ?:
                HabitsTrackerScreen.Start.name
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FabHabit(
                currentScreen = currentScreen,
                onClick = {
                    navController.navigate(HabitsTrackerScreen.AddNewHabit.name)
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
        //TODO("Вынести навигацию")
        NavHost(
            navController = navController,
            startDestination = HabitsTrackerScreen.Start.name,
            modifier = modifier.padding(contentPadding)
        ) {

            composable(route = HabitsTrackerScreen.Start.name) {
                HabitsListScreen(
                    onClickHabit = { idHabit ->
                        navController.navigate("${HabitsTrackerScreen.UpdateHabit.name}/${idHabit}")
                    }
                )
            }

            composable(route = HabitsTrackerScreen.AddNewHabit.name) {
                HabitScreen(
                    navigateBack = {
                        navController.navigate(HabitsTrackerScreen.Start.name)
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            composable(
                route = "${HabitsTrackerScreen.UpdateHabit.name}/{$ID_HABIT_PARAM_NAME}",
                arguments = listOf(navArgument("idHabit") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                HabitScreen(
                    navigateBack = {
                        navController.navigate(HabitsTrackerScreen.Start.name)
                    },
                    vm = viewModel(
                        factory = HabitViewModelFactory(
                            idHabit = backStackEntry.arguments?.getInt(ID_HABIT_PARAM_NAME)
                        )
                    ),
                    snackbarHostState = snackbarHostState
                )
            }

        }
    }
}

@Composable
fun FabHabit(
    currentScreen: HabitsTrackerScreen,
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
    currentScreen: HabitsTrackerScreen
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(currentScreen.screenTitle)
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
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