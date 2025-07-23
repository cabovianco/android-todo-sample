package com.cabovianco.todo.presentation.viewmodel

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.repository.TaskRepository
import com.cabovianco.todo.domain.usecase.InsertTaskUseCase
import com.cabovianco.todo.rule.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddTaskViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var taskRepository: TaskRepository
    private lateinit var addTaskViewModel: AddTaskViewModel

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        val insertTaskUseCase = InsertTaskUseCase(taskRepository)
        addTaskViewModel = AddTaskViewModel(insertTaskUseCase)
    }

    @Test
    fun addTaskViewModel_onValidTask_savesCorrectly() = runTest {
        val taskName = "New task"
        addTaskViewModel.onTaskNameChange(taskName)

        assertTrue(addTaskViewModel.isTaskValid())
        assertTrue(addTaskViewModel.addTask())

        val result = taskRepository.getAllTasks()
            .first()
            .first()

        assertEquals(taskName, result.name)
    }

    @Test
    fun addTaskViewModel_onInvalidTask_savesCorrectly() = runTest {
        val taskName = ""
        addTaskViewModel.onTaskNameChange(taskName)

        assertFalse(addTaskViewModel.isTaskValid())
        assertFalse(addTaskViewModel.addTask())

        val result = taskRepository.getAllTasks()
            .first()

        assertTrue(result.isEmpty())
    }
}
