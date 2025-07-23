package com.cabovianco.todo.data.local.repository

import com.cabovianco.todo.data.local.dao.FakeTaskDao
import com.cabovianco.todo.data.repository.TaskRepositoryImpl
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TaskRepositoryTest {
    private lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        val taskDao = FakeTaskDao()
        taskRepository = TaskRepositoryImpl(taskDao)
    }

    @Test
    fun insertTask_savesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val result = taskRepository.getTaskById(1)
            .first()

        assertEquals(task, result)
    }

    @Test
    fun updateTask_updatesCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val updatedTask = task.copy(name = "Updated task")
        taskRepository.updateTask(updatedTask)

        val result = taskRepository.getTaskById(1)
            .first()

        assertEquals(updatedTask, result)
    }

    @Test
    fun getById_returnsInsertedTask() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)

        val result = taskRepository.getTaskById(1)
            .first()

        assertEquals(task, result)
    }

    @Test
    fun delete_deletesTaskCorrectly() = runTest {
        val task = Task(id = 1, name = "New task", done = false)
        taskRepository.insertTask(task)
        taskRepository.deleteTask(task)

        val result = taskRepository.getAllTasks()
            .first()

        assertTrue(result.isEmpty())
    }
}
