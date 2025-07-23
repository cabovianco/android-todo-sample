package com.cabovianco.todo.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cabovianco.todo.presentation.ui.components.TaskFormScreen

@Composable
fun EditTaskScreen(
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    isTaskValid: Boolean,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    TaskFormScreen(
        taskName = taskName,
        onTaskNameChange = onTaskNameChange,
        isTaskValid = isTaskValid,
        onCancel = onCancel,
        onSave = onSave,
        modifier = modifier
    )
}
