package com.cabovianco.todo.presentation.state

import com.cabovianco.todo.domain.model.Task

sealed interface MainUiState {
    data class Success(val tasks: List<Task> = emptyList()) : MainUiState
    data object Error : MainUiState
    data object Loading : MainUiState
}
