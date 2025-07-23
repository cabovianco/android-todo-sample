package com.cabovianco.todo.presentation.viewmodel

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import com.cabovianco.todo.domain.usecase.DeleteTaskUseCase
import com.cabovianco.todo.domain.usecase.GetAllTasksUseCase
import com.cabovianco.todo.domain.usecase.UpdateTaskUseCase
import com.cabovianco.todo.presentation.state.MainUiState
import com.cabovianco.todo.rule.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainTaskViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var taskRepository: TaskRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        val getAllTasksUseCase = GetAllTasksUseCase(taskRepository)
        val updateTaskUseCase = UpdateTaskUseCase(taskRepository)
        val deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
        mainViewModel = MainViewModel(getAllTasksUseCase, updateTaskUseCase, deleteTaskUseCase)
    }

    @Test
    fun mainViewModel_initialState_isLoading() {
        val state = mainViewModel.uiState.value
        assertTrue(state == MainUiState.Loading)
    }

    @Test
    fun mainViewModel_onCheckedChange_taskUpdatesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        mainViewModel.onCheckedChange(task, true)

        val updatedTask  = taskRepository.getTaskById(1)
            .first()

        assertTrue(updatedTask.done)
    }

    @Test
    fun mainViewModel_deleteTask_deletesTaskCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        mainViewModel.deleteTask(task)

        val tasks = taskRepository.getAllTasks()
            .first()

        assertTrue(tasks.isEmpty())
    }
}
