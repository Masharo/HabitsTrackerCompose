package com.masharo.habitstrackercompose.navigate

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModelFactory
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitsListScreen

const val ID_HABIT_PARAM_NAME = "idHabit"

fun NavGraphBuilder.navigateToStartScreen(
    navController: NavHostController
) {
    composable(route = HabitNavigateState.Start.name) {
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
        arguments = listOf(navArgument(ID_HABIT_PARAM_NAME) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
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

fun NavGraphBuilder.navigateToAddNewHabit(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(route = HabitNavigateState.AddNewHabit.name) {
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
            },
            snackbarHostState = snackbarHostState
        )
    }
}