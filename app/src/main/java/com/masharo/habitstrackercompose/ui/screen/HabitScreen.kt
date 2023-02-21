package com.masharo.habitstrackercompose.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.habitstrackercompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(
    modifier: Modifier = Modifier
) {

    var selectedType by remember { mutableStateOf(0) }
    var selectedPriority by remember { mutableStateOf(R.string.priority_low_for_spinner) }
    var expanded by remember { mutableStateOf(false) }

    val listPriority = listOf(
        R.string.priority_low_for_spinner,
        R.string.priority_middle_for_spinner,
        R.string.priority_high_for_spinner
    )

    val listType = listOf(
        R.string.type_positive_for_radio_button,
        R.string.type_negative_for_radio_button
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Название привычки")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {
                Text(text = "Описание привычки")
            }
        )

        Text(
            modifier = Modifier
                .clickable { expanded = true },
            text = stringResource(selectedPriority)
        )

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listPriority.forEach { idPriority ->
                DropdownMenuItem(
                    text = {
                        Text(stringResource(idPriority))
                    },
                    onClick = {
                        selectedPriority = idPriority
                        expanded = false
                    }
                )
            }
        }

        listType.forEachIndexed { id, type ->
            Row(
                modifier = Modifier
                    .clickable {
                        selectedType = id
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = id == selectedType,
                    onClick = {
                        selectedType = id
                    }
                )

                Text(stringResource(type))
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun HabitScreenPreview() {
    HabitScreen()
}