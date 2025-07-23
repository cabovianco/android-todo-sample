package com.cabovianco.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.usecase.DeleteTaskUseCase
import com.cabovianco.todo.domain.usecase.GetAllTasksUseCase
import com.cabovianco.todo.domain.usecase.UpdateTaskUseCase
import com.cabovianco.todo.presentation.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    val uiState: StateFlow<MainUiState> = getAllTasksUseCase()
        .map<List<Task>, MainUiState> { tasks -> MainUiState.Success(tasks) }
        .catch { emit(MainUiState.Error) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MainUiState.Loading
        )

    fun onCheckedChange(task: Task, value: Boolean) {
        viewModelScope.launch {
            val updatedTask = task.copy(done = value)
            updateTaskUseCase(updatedTask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}
