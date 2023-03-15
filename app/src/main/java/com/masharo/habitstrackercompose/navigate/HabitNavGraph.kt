//package com.masharo.habitstrackercompose.navigate
//
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavType
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import com.masharo.habitstrackercompose.ui.screen.HabitsTrackerScreen
//import com.masharo.habitstrackercompose.ui.screen.ID_HABIT_PARAM_NAME
//import com.masharo.habitstrackercompose.ui.screen.habit.HabitScreen
//import com.masharo.habitstrackercompose.ui.screen.habit.HabitViewModelFactory
//import com.masharo.habitstrackercompose.ui.screen.habitsList.HabitsListScreen
//
//fun NavGraphBuilder.habitGraph() {
//    composable(route = HabitsTrackerScreen.Start.name) {
//        HabitsListScreen(
//            onClickHabit = { idHabit ->
//                navController.navigate("${HabitsTrackerScreen.UpdateHabit.name}/${idHabit}")
//            }
//        )
//    }
//
//    composable(route = HabitsTrackerScreen.AddNewHabit.name) {
//        HabitScreen(
//            navigateBack = {
//                navController.navigate(HabitsTrackerScreen.Start.name)
//            },
//            snackbarHostState = snackbarHostState
//        )
//    }
//
//    composable(
//        route = "${HabitsTrackerScreen.UpdateHabit.name}/{$ID_HABIT_PARAM_NAME}",
//        arguments = listOf(navArgument("idHabit") {
//            type = NavType.IntType
//        })
//    ) { backStackEntry ->
//        HabitScreen(
//            navigateBack = {
//                navController.navigate(HabitsTrackerScreen.Start.name)
//            },
//            vm = viewModel(
//                factory = HabitViewModelFactory(
//                    idHabit = backStackEntry.arguments?.getInt(ID_HABIT_PARAM_NAME)
//                )
//            ),
//            snackbarHostState = snackbarHostState
//        )
//    }
//}