package com.masharo.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.masharo.core.R

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    title: String,
    items: Iterable<String>,
    onSelectItem: (item: Int) -> Unit
) {
    var isExpandedPriorities by rememberSaveable { mutableStateOf(false) }
    val arrowRotate by animateFloatAsState(
        targetValue = if (isExpandedPriorities) 180f else 0f
    )

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    isExpandedPriorities = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .rotate(arrowRotate),
                painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = null
            )
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = isExpandedPriorities,
            onDismissRequest = { isExpandedPriorities = false }
        ) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(
                    text = {
                        Text(value)
                    },
                    onClick = {
                        onSelectItem(index)
                        isExpandedPriorities = false
                    }
                )
            }
        }
    }
}