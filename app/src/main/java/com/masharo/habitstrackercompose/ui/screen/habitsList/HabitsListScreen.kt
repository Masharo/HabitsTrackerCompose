package com.masharo.habitstrackercompose.ui.screen.habitsList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.habits
import com.masharo.habitstrackercompose.model.HabitListItemUiState
import com.masharo.habitstrackercompose.model.toHabitListItemUiState

@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    vm: HabitListViewModel = viewModel(
        factory = HabitListViewModelFactory(
            habits
        )
    ),
    onClickHabit: (idHabit: Int) -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        itemsIndexed(uiState.habits) { id, habit ->
            HabitItem(
                habit = habit,
                onClick = {
                    onClickHabit(id)
                },
                isFirstItem = id == 0,
                isLastItem = id == habits.lastIndex
            )
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
    var isVisibleOptional by remember {
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

@Preview(
    showBackground = true
)
@Composable
fun HabitItemPreview() {
    HabitItem(
        habit = habits[0].toHabitListItemUiState(),
        onClick = {

        },
        isFirstItem = false,
        isLastItem = false
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HabitsListScreenPreview() {
//    HabitsListScreen(
//        habitsList = listOf(
//            Habit(
//                title = "title1",
//                description = "description1"
//            ),
//            Habit(
//                title = "title2",
//                description = "description2"
//            ),
//            Habit(
//                title = "title3",
//                description = "description3"
//            ),
//            Habit(
//                title = "title4",
//                description = "description4",
//                color = Color.Red
//            )
//        ),
//        onClickHabit = {}
//    )
}