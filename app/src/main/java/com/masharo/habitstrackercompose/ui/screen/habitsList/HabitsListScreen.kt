package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.masharo.core.ui.Spinner
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import com.masharo.habitstrackercompose.model.Page
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    vm: HabitListViewModel = koinViewModel(),
    onClickHabit: (idHabit: Long) -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .clearFocusOnKeyboardDismiss()
                        .fillMaxWidth(),
                    label = {
                        Text("Фильтр")
                    },
                    value = uiState.search,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    onValueChange = {
                        vm.searchUpdate(it)
                    }
                )
                Spinner(
                    title = stringResource(uiState.columnSort.selectedTitle),
                    items = ColumnSort.values().map { stringResource(it.title) },
                    onSelectItem = {
                        vm.columnSortUpdate(ColumnSort.values()[it])
                    }
                )
                Spinner(
                    title = stringResource(uiState.typeSort.selectedTitle),
                    items = TypeSort.values().map { stringResource(it.title) },
                    onSelectItem = {
                        vm.typeSortUpdate(TypeSort.values()[it])
                    }
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                uiState.pages.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(title),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                modifier = modifier
                    .weight(1f),
                state = pagerState,
                pageCount = uiState.countPage
            ) { page ->
                val habitsPage = when (Page.values()[page]) {
                    Page.POSITIVE_HABIT_LIST -> uiState.habitsPositive
                    else -> uiState.habitsNegative
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    itemsIndexed(habitsPage) { index, habit ->
                        HabitItem(
                            habit = habit,
                            onClick = {
                                onClickHabit(habit.id)
                            },
                            isFirstItem = index == 0,
                            isLastItem = index == habitsPage.lastIndex
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habit: HabitListItemUiState,
    onClick: () -> Unit,
    isFirstItem: Boolean,
    isLastItem: Boolean
) {
    var isVisibleOptional by rememberSaveable {
        mutableStateOf(false)
    }

    val sizePadding = 10.dp

    val arrowRotate by animateFloatAsState(
        targetValue = if (isVisibleOptional) 180f else 0f
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = sizePadding,
                end = sizePadding,
                top = if (isFirstItem) sizePadding else 0.dp,
                bottom = if (isLastItem) sizePadding else 0.dp
            )
            .clickable {
                onClick()
            }
        ,
        elevation = cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Box {
            habit.color?.let { color ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(
                                10.dp
                            )
                        )
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 10.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = habit.title
                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        modifier = Modifier
                            .clickable { isVisibleOptional = !isVisibleOptional }
                            .rotate(arrowRotate),
                        painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = stringResource(R.string.dropdown_habit_description)
                    )
                }

                AnimatedVisibility(
                    visible = isVisibleOptional
                ) {
                    Column {
                        Text(text = habit.description)
                        Text(text = stringResource(habit.priority.stringResValue))
                        Text(text = stringResource(habit.type.stringResValue))
                        Text(text = stringResource(R.string.period_title, habit.period))
                        Text(
                            text = stringResource(
                                R.string.count_ready_habit,
                                habit.countReady,
                                habit.count
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss (): Modifier = composed {
    var isFocused by remember { mutableStateOf (false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf (false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect (imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus ()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}