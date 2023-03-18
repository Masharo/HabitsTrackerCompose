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
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.habitsFlow
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    vm: HabitListViewModel = viewModel(
        factory = HabitListViewModelFactory(
            habitsFlow
        )
    ),
    onClickHabit: (idHabit: Int) -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ) {
            uiState.pages.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
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
            pageCount = uiState.countPage
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                itemsIndexed(uiState.habits) { id, habit ->
                    HabitItem(
                        habit = habit,
                        onClick = {
                            onClickHabit(id)
                        },
                        isFirstItem = id == 0,
                        isLastItem = id == uiState.habits.lastIndex
                    )
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