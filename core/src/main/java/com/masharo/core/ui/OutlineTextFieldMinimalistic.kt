package com.masharo.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun OutlineTextFieldMinimalistic(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        value = value,
        singleLine = singleLine,
        isError = isError,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        label = {
            Text(text = stringResource(label))
        }
    )
}