package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTaskByIdUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        getTaskByIdUseCase = GetTaskByIdUseCase(taskRepository)
    }

    @Test
    fun getById_returnsInsertedTask() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val result = getTaskByIdUseCase(1)
            .first()

        assertEquals(task, result)
    }
}
