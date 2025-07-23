package com.cabovianco.todo.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cabovianco.todo.R
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.presentation.state.MainUiState

@Composable
fun MainScreen(
    uiState: MainUiState,
    onClickTask: (Int) -> Unit,
    onLongClickTask: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit
) {
    when (uiState) {
        is MainUiState.Success -> OnSuccess(
            tasks = uiState.tasks,
            onClickTask = onClickTask,
            onLongClickTask = onLongClickTask,
            onCheckedChange = onCheckedChange
        )

        is MainUiState.Error -> OnError(modifier = Modifier.fillMaxSize())
        is MainUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun OnSuccess(
    tasks: List<Task>,
    onClickTask: (Int) -> Unit,
    onLongClickTask: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyTasksList(modifier = Modifier.fillMaxSize())
    } else {
        ListTasks(
            tasks = tasks,
            onClickTask = onClickTask,
            onLongClickTask = onLongClickTask,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun EmptyTasksList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_tasks_added),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun ListTasks(
    tasks: List<Task>,
    onClickTask: (Int) -> Unit,
    onLongClickTask: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 16.dp,
            bottom = 8.dp
        )
    ) {
        tasks.forEach {
            item {
                TaskItem(
                    task = it,
                    onClick = onClickTask,
                    onLongClick = onLongClickTask,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskItem(
    task: Task,
    onClick: (Int) -> Unit,
    onLongClick: (Task) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDeleteDialogActive = rememberSaveable { mutableStateOf(false) }
    if (isDeleteDialogActive.value) {
        DeleteTaskDialog(
            isDeleteDialogActive = isDeleteDialogActive,
            onConfirmation = { onLongClick(task) }
        )
    }

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onLongClick = { isDeleteDialogActive.value = true },
                onClick = { onClick(task.id) }
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.name,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Checkbox(
                checked = task.done,
                onCheckedChange = { onCheckedChange(task, it) }
            )
        }
    }
}

@Composable
private fun DeleteTaskDialog(
    isDeleteDialogActive: MutableState<Boolean>,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { isDeleteDialogActive.value = false },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(R.string.delete_button))
            }
        },
        dismissButton = {
            TextButton(onClick = { isDeleteDialogActive.value = false }) {
                Text(
                    text = stringResource(R.string.cancel_button)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.delete_dialog_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = { Text(text = stringResource(R.string.delete_dialog_desc)) }
    )
}

@Composable
private fun OnError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.error),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
private fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
