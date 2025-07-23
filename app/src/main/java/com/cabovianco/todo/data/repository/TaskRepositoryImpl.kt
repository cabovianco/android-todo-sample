package com.cabovianco.todo.data.repository

import com.cabovianco.todo.data.local.dao.TaskDao
import com.cabovianco.todo.data.local.entity.toDomain
import com.cabovianco.todo.domain.model.Task
import com.cabovianco.todo.domain.model.toEntity
import com.cabovianco.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun insertTask(task: Task) =
        taskDao.insert(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.update(task.toEntity())

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAll()
            .map { tasks ->
                tasks.map { it.toDomain() }
            }

    override fun getTaskById(id: Int): Flow<Task> =
        taskDao.getById(id)
            .map {
                it.toDomain()
            }

    override suspend fun deleteTask(task: Task) =
        taskDao.delete(task.toEntity())
}
