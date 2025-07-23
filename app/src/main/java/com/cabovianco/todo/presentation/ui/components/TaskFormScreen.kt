package com.cabovianco.todo.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cabovianco.todo.R

@Composable
fun TaskFormScreen(
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    isTaskValid: Boolean,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = taskName,
            onValueChange = { onTaskNameChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.name_field)) },
            isError = !isTaskValid,
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val rowModifier = Modifier.weight(0.5f)

            OutlinedButton(
                modifier = rowModifier,
                onClick = { onCancel() }
            ) {
                Text(text = stringResource(R.string.cancel_button))
            }

            Spacer(rowModifier)

            Button(
                modifier = rowModifier,
                onClick = { onSave() }
            ) {
                Text(text = stringResource(R.string.save_button))
            }
        }
    }
}
