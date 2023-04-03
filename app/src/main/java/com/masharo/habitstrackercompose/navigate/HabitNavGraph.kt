package com.masharo.habitstrackercompose.navigate

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.masharo.habitstrackercompose.ui.screen.applicationInfo.ApplicationInfoScreen
import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitsListScreen
import com.masharo.habitstrackercompose.ui.screen.splash.SplashScreenHabit
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val ID_HABIT_PARAM_NAME = "idHabit"

fun NavGraphBuilder.navigateToHabitListScreen(
    navController: NavHostController
) {
    composable(route = HabitNavigateState.HabitsList.name) {
        HabitsListScreen(
            onClickHabit = { idHabit ->
                navController.navigate("${HabitNavigateState.UpdateHabit.name}/${idHabit}")
            }
        )
    }
}

fun NavGraphBuilder.navigateToUpdateHabitScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(
        route = "${HabitNavigateState.UpdateHabit.name}/{$ID_HABIT_PARAM_NAME}",
        arguments = listOf(
            navArgument(ID_HABIT_PARAM_NAME) {
                type = NavType.LongType
            }
        )
    ) { backStackEntry ->
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
            },
            vm = koinViewModel(
                parameters = {
                    parametersOf(backStackEntry.arguments?.getLong(ID_HABIT_PARAM_NAME))
                }
            ),
//            viewModel(
//                factory = HabitViewModelFactory(
//                    idHabit = backStackEntry.arguments?.getLong(ID_HABIT_PARAM_NAME)
//                )
//            ),
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavGraphBuilder.navigateToAddNewHabit(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(route = HabitNavigateState.AddNewHabit.name) {
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
            },
            vm = koinViewModel(),
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavGraphBuilder.navigateToApplicationInfo() {
    composable(route = HabitNavigateState.ApplicationInfo.name) {
        ApplicationInfoScreen()
    }
}

fun NavGraphBuilder.navigateToSplash(
    navController: NavHostController
) {
    composable(route = HabitNavigateState.Splash.name) {
        SplashScreenHabit(
            navigateToNextScreen = {
                navController.popBackStack()
                navController.navigate(HabitNavigateState.HabitsList.name)
            }
        )
    }
}