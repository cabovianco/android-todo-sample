package com.cabovianco.todo.data.local.repository

import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository : TaskRepository {
    private val tasks = mutableListOf<Task>()

    override suspend fun insertTask(task: Task) {
        tasks.add(task)
    }

    override suspend fun updateTask(task: Task) {
        tasks.forEachIndexed { i, t ->
            if (t.id == task.id) {
                tasks[i] = task
            }
        }
    }

    override fun getAllTasks(): Flow<List<Task>> = flow {
        emit(tasks.toList())
    }

    override fun getTaskById(id: Int): Flow<Task> = flow {
        emit(tasks.first { it.id == id })
    }

    override suspend fun deleteTask(task: Task) {
        tasks.removeIf { it.id == task.id }
    }
}
