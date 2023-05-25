package com.masharo.habitstrackercompose.navigate

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.masharo.habitstrackercompose.app.viewModelComponent
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.HabitViewModelSupportInject
import com.masharo.habitstrackercompose.di.dagger.viewmodelutils.daggerViewModel
import com.masharo.habitstrackercompose.ui.screen.applicationInfo.ApplicationInfoScreen
import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitsListScreen

const val ID_HABIT_PARAM_NAME = "idHabit"

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.navigateToHabitListScreen(
    navController: NavHostController,
    bottomSheetState: BottomSheetScaffoldState,
    isNeedRefresh: MutableState<Boolean>
) {
    composable(route = HabitNavigateState.Home.name) {
        val context = LocalContext.current
        HabitsListScreen(
            bottomSheetState = bottomSheetState,
            onClickHabit = { idHabit ->
                navController.navigate("${HabitNavigateState.UpdateHabit.name}/${idHabit}")
            },
            vm = daggerViewModel {
                context.viewModelComponent.getHabitListViewModel()
            },
            isNeedRefresh = isNeedRefresh
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
        val context = LocalContext.current
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
            },
            vm = daggerViewModel {
                HabitViewModelSupportInject(
                    viewModelComponent = context.viewModelComponent,
                    idHabit = backStackEntry.arguments?.getLong(ID_HABIT_PARAM_NAME)
                ).getHabitViewModel()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavGraphBuilder.navigateToAddNewHabit(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    composable(route = HabitNavigateState.AddNewHabit.name) {
        val context = LocalContext.current
        HabitScreen(
            navigateBack = {
                navController.navigateUp()
            },
            vm = daggerViewModel {
                HabitViewModelSupportInject(
                    viewModelComponent = context.viewModelComponent
                ).getHabitViewModel()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavGraphBuilder.navigateToApplicationInfo() {
    composable(route = HabitNavigateState.ApplicationInfo.name) {
        ApplicationInfoScreen()
    }
}