package com.masharo.habitstrackercompose.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.habitstrackercompose.R
import com.masharo.habitstrackercompose.data.habits
import com.masharo.habitstrackercompose.model.Habit

@Composable
fun HabitsListScreen(
    modifier: Modifier = Modifier,
    habitsList: List<Habit> = habits
) {

    val habits by remember {
        mutableStateOf(habitsList)
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        items(habits) { habit ->
            HabitItem(habit = habit)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitItem(
    modifier: Modifier = Modifier,
    habit: Habit
) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    val isArrowRotate by animateFloatAsState(targetValue = if (isVisible) 180f else 0f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .animateContentSize { initialValue, targetValue -> }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
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
                        .clickable { isVisible = !isVisible }
                        .rotate(isArrowRotate),
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = isVisible) {
                Column {
                    Text(text = habit.description)
                    Text(text = stringResource(habit.priority))
                    Text(text = stringResource(habit.type))
                    Text(text = habit.period)
                    Text(
                        text = stringResource(
                            R.string.count_ready_habit,
                            habit.count,
                            habit.countReady
                        )
                    )
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
            Habit(
                title = "title1",
                description = "description1"
            ),
            Habit(
                title = "title2",
                description = "description2"
            ),
            Habit(
                title = "title3",
                description = "description3"
            ),
            Habit(
                title = "title4",
                description = "description4"
            )
        )
    )
}