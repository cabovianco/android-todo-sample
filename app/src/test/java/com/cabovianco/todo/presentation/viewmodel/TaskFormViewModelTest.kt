package com.cabovianco.todo.presentation.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TaskFormViewModelTest {
    private lateinit var taskFormViewModel: TaskFormViewModel

    @Before
    fun setUp() {
        taskFormViewModel = TaskFormViewModel()
    }

    @Test
    fun taskFormViewModel_onTaskNameChange_taskNameChangesCorrectly() {
        val taskName = "New task"
        taskFormViewModel.onTaskNameChange(taskName)

        val result = taskFormViewModel.uiState.value.taskName

        assertEquals(taskName, result)
    }

    @Test
    fun taskFormViewModel_onValidTask_isTaskValidIsTrue() {
        val taskName = "New task"
        taskFormViewModel.onTaskNameChange(taskName)

        val result = taskFormViewModel.isTaskValid()

        assertTrue(result)
    }

    @Test
    fun taskFormViewModel_onInvalidTask_isTaskValidIsTrue() {
        val taskName = ""
        taskFormViewModel.onTaskNameChange(taskName)

        val result = taskFormViewModel.isTaskValid()

        assertFalse(result)
    }
}
