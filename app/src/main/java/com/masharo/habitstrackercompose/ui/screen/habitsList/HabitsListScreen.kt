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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.habits
import com.masharo.habitstrackercompose.model.HabitUiState

@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    habitsList: List<HabitUiState> = habits,
    onClickHabit: (idHabit: Int) -> Unit
) {

    val habits by remember {
        mutableStateOf(habitsList)
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        itemsIndexed(habits) { id, habit ->
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
    habit: HabitUiState,
    onClick: () -> Unit,
    isFirstItem: Boolean,
    isLastItem: Boolean
) {

    var isVisibleOptional by remember {
        mutableStateOf(false)
    }

    val sizePadding = 10.dp

    val isArrowRotate by animateFloatAsState(
        targetValue = if (isVisibleOptional) 180f else 0f
    )
    var sizeCard by remember {
        mutableStateOf(0)
    }

    var sizeBottom by remember {
        mutableStateOf(0)
    }

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
            .onSizeChanged {
                sizeCard = it.height
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
                        .fillMaxHeight()
                        .width(8.dp)
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
                            .rotate(isArrowRotate),
                        painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = null //TODO("Описание")
                    )
                }

                AnimatedVisibility(
                    visible = isVisibleOptional
                ) {
                    Column {
                        Text(text = habit.description)
                        Text(text = stringResource(habit.priority.stringResValue))
                        Text(text = stringResource(habit.type.stringResValue))
                        Text(text = habit.period)
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
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HabitsListScreenPreview() {
    HabitsListScreen(
        habitsList = listOf(
            HabitUiState(
                title = "title1",
                description = "description1"
            ),
            HabitUiState(
                title = "title2",
                description = "description2"
            ),
            HabitUiState(
                title = "title3",
                description = "description3"
            ),
            HabitUiState(
                title = "title4",
                description = "description4",
                color = Color.Red
            )
        ),
        onClickHabit = {}
    )
}