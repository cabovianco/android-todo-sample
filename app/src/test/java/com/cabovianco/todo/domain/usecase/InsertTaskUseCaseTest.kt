package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.data.local.repository.FakeTaskRepository
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InsertTaskUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var insertTaskUseCase: InsertTaskUseCase

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        insertTaskUseCase = InsertTaskUseCase(taskRepository)
    }

    @Test
    fun insertTask_savesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        insertTaskUseCase(task)

        val result = taskRepository.getTaskById(1)
            .first()

        assertEquals(task, result)
    }
}
