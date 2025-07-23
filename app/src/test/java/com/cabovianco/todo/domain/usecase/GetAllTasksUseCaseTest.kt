package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertContains

class GetAllTasksUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var getAllTasksUseCase: GetAllTasksUseCase

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        getAllTasksUseCase = GetAllTasksUseCase(taskRepository)
    }

    @Test
    fun getAllTasks_returnsInsertedTasks() = runTest {
        val tasks = listOf(
            Task(id = 1, name = "New task", done = false),
            Task(id = 2, name = "New task", done = false),
            Task(id = 3, name = "New task", done = false),
        )

        tasks.forEach { taskRepository.insertTask(it) }

        val result = getAllTasksUseCase()
            .first()

        result.forEach {
            assertContains(tasks, it)
        }
    }
}
