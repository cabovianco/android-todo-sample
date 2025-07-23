package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    }

    @Test
    fun deleteTask_deletesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        deleteTaskUseCase(task)

        val result = taskRepository.getAllTasks()
            .first()

        assertTrue(result.isEmpty())
    }
}
