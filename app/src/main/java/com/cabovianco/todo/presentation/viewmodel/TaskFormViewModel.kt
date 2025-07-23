package com.cabovianco.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.cabovianco.todo.presentation.state.TaskFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class TaskFormViewModel : ViewModel() {
    protected val mutableUiState: MutableStateFlow<TaskFormUiState> = MutableStateFlow(TaskFormUiState())
    val uiState get(): StateFlow<TaskFormUiState> = mutableUiState.asStateFlow()

    fun onTaskNameChange(value: String) {
        mutableUiState.update {
            it.copy(taskName = value, isTaskValid = true)
        }
    }

    fun isTaskValid(): Boolean {
        val isValid = mutableUiState.value.taskName.isNotBlank()
        mutableUiState.update {
            it.copy(isTaskValid = isValid)
        }

        return isValid
    }
}
