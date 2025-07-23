package com.cabovianco.todo.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.usecase.InsertTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val insertTaskUseCase: InsertTaskUseCase
) : TaskFormViewModel() {
    fun addTask(): Boolean {
        if (!isTaskValid()) {
            return false
        }

        viewModelScope.launch {
            val task = Task(name = mutableUiState.value.taskName)
            insertTaskUseCase(task)
        }

        return true
    }
}
