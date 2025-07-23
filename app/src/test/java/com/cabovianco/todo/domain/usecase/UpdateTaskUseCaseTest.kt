package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        updateTaskUseCase = UpdateTaskUseCase(taskRepository)
    }

    @Test
    fun updateTask_updatesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val updatedTask = task.copy(name = "Updated task")
        updateTaskUseCase(updatedTask)

        val result = taskRepository.getTaskById(1)
            .first()

        assertEquals(updatedTask, result)
    }
}
