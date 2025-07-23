package com.cabovianco.todo.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.usecase.GetTaskByIdUseCase
import com.cabovianco.todo.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : TaskFormViewModel() {
    fun loadTask(id: Int) {
        viewModelScope.launch {
            getTaskByIdUseCase(id)
                .collect { task ->
                    mutableUiState.update {
                        it.copy(taskId = id, taskName = task.name, isTaskDone = task.done)
                    }
                }
        }
    }

    fun saveTask(): Boolean {
        if (!isTaskValid()) {
            return false
        }

        viewModelScope.launch {
            val task = Task(
                id = mutableUiState.value.taskId,
                name = mutableUiState.value.taskName,
                done = mutableUiState.value.isTaskDone
            )

            updateTaskUseCase(task)
        }

        return true
    }
}
