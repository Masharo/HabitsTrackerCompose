package com.masharo.habitstrackercompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    ModalNavigationDrawer(
        drawerContent = {
            HabitDrawer(
                currentScreen = currentScreen,
                navController = navController
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                FabHabit(
                    isNeedFab = currentScreen.isHaveFabAddHabit &&
                                bottomSheetScaffoldState.bottomSheetState.currentValue != SheetValue.Expanded,
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
                if (currentScreen.isHaveTopAppBar)
                    HabitsTrackerAppBar(
                        currentScreen = currentScreen
                    )
            }
        ) { contentPadding ->
            HabitNavHost(
                navController = navController,
                modifier = modifier,
                contentPadding = contentPadding,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Composable
private fun HabitDrawer(
    currentScreen: HabitNavigateState,
    navController: NavHostController
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(IntrinsicSize.Min)
    ) {
        HabitDrawer(
            topHabitScreen = currentScreen,
            navigateBackToStart = {
                navController.popBackStack(
                    route = HabitNavigateState.Home.name,
                    inclusive = false
                )
            },
            navigateToApplicationInfo = {
                navController.navigate(
                    route = HabitNavigateState.ApplicationInfo.name
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HabitNavHost(
    navController: NavHostController,
    modifier: Modifier,
    contentPadding: PaddingValues,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = HabitNavigateState.Home.name,
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {

        navigateToHabitListScreen(
            bottomSheetState = bottomSheetScaffoldState,
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

        navigateToApplicationInfo()

    }
}

//Нельзя создать более одного экрана для каждого пункта меню.
//Нельзя вернуться на побочные экраны.
@Composable
private fun HabitDrawer(
    modifier: Modifier = Modifier,
    topHabitScreen: HabitNavigateState,
    navigateBackToStart: () -> Unit,
    navigateToApplicationInfo: () -> Unit
) {
    val isBackStackHaveInfoScreen = topHabitScreen == HabitNavigateState.ApplicationInfo

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 14.dp
                ),
            text = stringResource(R.string.title_drawer),
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
            modifier = Modifier
                .fillMaxWidth(),
            onClick = navigateBackToStart,
            enabled = isBackStackHaveInfoScreen
        ) {
            Text(
                text = stringResource(R.string.home_screen)
            )
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = navigateToApplicationInfo,
            enabled = !isBackStackHaveInfoScreen
        ) {
            Text(
                text = stringResource(R.string.application_info)
            )
        }
    }
}

@Composable
fun FabHabit(
    isNeedFab: Boolean,
    onClick: () -> Unit
) {
    if (isNeedFab) {
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
            currentScreen.screenTitle?.let { title ->
                Text(text = stringResource(title))
            }
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