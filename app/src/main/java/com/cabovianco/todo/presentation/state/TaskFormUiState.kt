package com.cabovianco.todo.presentation.state

data class TaskFormUiState(
    val taskId: Int = 0,
    val taskName: String = "",
    val isTaskDone: Boolean = false,
    val isTaskValid: Boolean = true
)
