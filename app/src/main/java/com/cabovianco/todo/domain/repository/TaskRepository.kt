package com.cabovianco.todo.domain.repository

import com.cabovianco.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    fun getAllTasks(): Flow<List<Task>>

    fun getTaskById(id: Int): Flow<Task>

    suspend fun deleteTask(task: Task)
}
