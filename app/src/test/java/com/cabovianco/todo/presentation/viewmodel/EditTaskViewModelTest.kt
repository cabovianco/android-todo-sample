package com.cabovianco.todo.presentation.viewmodel

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import com.cabovianco.todo.domain.usecase.GetTaskByIdUseCase
import com.cabovianco.todo.domain.usecase.UpdateTaskUseCase
import com.cabovianco.todo.presentation.state.TaskFormUiState
import com.cabovianco.todo.rule.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditTaskViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var taskRepository: TaskRepository
    private lateinit var editTaskViewModel: EditTaskViewModel

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        val getTaskByIdUseCase = GetTaskByIdUseCase(taskRepository)
        val updateTaskUseCase = UpdateTaskUseCase(taskRepository)
        editTaskViewModel = EditTaskViewModel(getTaskByIdUseCase, updateTaskUseCase)
    }

    @Test
    fun editTaskViewModel_editInsertedTask_loadsCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        var state = editTaskViewModel.uiState.value
        assertEquals(TaskFormUiState(), state)

        editTaskViewModel.loadTask(1)

        state = editTaskViewModel.uiState.value
        assertEquals(task.id, state.taskId)
        assertEquals(task.name, state.taskName)
        assertEquals(task.done, state.isTaskDone)
    }

    @Test
    fun editTaskViewModel_editInsertedTask_savesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val updatedTaskName = "Updated task"
        editTaskViewModel.onTaskNameChange(updatedTaskName)

        assertTrue(editTaskViewModel.saveTask())
        assertEquals(editTaskViewModel.uiState.value.taskName, updatedTaskName)
    }
}
