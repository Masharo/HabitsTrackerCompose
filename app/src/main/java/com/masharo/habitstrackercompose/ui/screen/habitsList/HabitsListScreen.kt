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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.masharo.core.ui.Spinner
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import com.masharo.habitstrackercompose.model.HabitListUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    vm: HabitListViewModel = koinViewModel(),
    onClickHabit: (idHabit: Long) -> Unit,
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState()
) {
    val uiState by vm.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            SearchAndSortHabit(
                uiState = uiState,
                searchText = vm::searchUpdate,
                columnSort = vm::columnSortUpdate,
                typeSort = vm::typeSortUpdate
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
        ) {
            HabitsTypeTabRow(
                modifier = Modifier
                    .weight(1f),
                pagerState = pagerState,
                uiState = uiState,
                scope = scope
            ) { page ->
                HabitsPage(
                    page = page,
                    uiState = uiState,
                    onClickHabit = onClickHabit
                )
            }
        }
    }
}

@Composable
private fun SearchAndSortHabit(
    uiState: HabitListUiState,
    searchText: (String) -> Unit,
    columnSort: (ColumnSort) -> Unit,
    typeSort: (TypeSort) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(stringResource(R.string.search_title))
            },
            value = uiState.search,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            onValueChange = searchText
        )
        Spinner(
            title = stringResource(uiState.columnSort.selectedTitle),
            items = ColumnSort.values().map { stringResource(it.title) },
            onSelectItem = {
                columnSort(ColumnSort.values()[it])
            }
        )
        Spinner(
            title = stringResource(uiState.typeSort.selectedTitle),
            items = TypeSort.values().map { stringResource(it.title) },
            onSelectItem = {
                typeSort(TypeSort.values()[it])
            }
        )
    }
}

@Composable
private fun HabitsPage(
    page: Int,
    uiState: HabitListUiState,
    onClickHabit: (idHabit: Long) -> Unit
) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HabitsTypeTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    uiState: HabitListUiState,
    scope: CoroutineScope,
    content: @Composable (page: Int) -> Unit
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
        modifier = modifier,
        state = pagerState,
        pageCount = uiState.countPage,
        pageContent = content
    )
}

@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habit: HabitListItemUiState,
    onClick: () -> Unit,
    isFirstItem: Boolean,
    isLastItem: Boolean
) {
    val sizePadding = 10.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = sizePadding,
                end = sizePadding,
                top = if (isFirstItem) sizePadding else 0.dp,
                bottom = if (isLastItem) 100.dp else 0.dp
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

                val isVisibleOptional = rememberSaveable {
                    mutableStateOf(false)
                }

                HabitItemVisibleParam(
                    isVisibleOptional = isVisibleOptional,
                    habit = habit
                )

                HabitItemOptionalParam(
                    isVisibleOptional = isVisibleOptional,
                    habit = habit
                )
            }
        }
    }
}

@Composable
private fun HabitItemOptionalParam(
    isVisibleOptional: MutableState<Boolean>,
    habit: HabitListItemUiState
) {
    AnimatedVisibility(
        visible = isVisibleOptional.value
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

@Composable
private fun HabitItemVisibleParam(
    isVisibleOptional: MutableState<Boolean>,
    habit: HabitListItemUiState
) {
    val arrowRotate by animateFloatAsState(
        targetValue = if (isVisibleOptional.value) 180f else 0f
    )

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
                .clickable { isVisibleOptional.value = !isVisibleOptional.value }
                .rotate(arrowRotate),
            painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
            contentDescription = stringResource(R.string.dropdown_habit_description)
        )
    }
}