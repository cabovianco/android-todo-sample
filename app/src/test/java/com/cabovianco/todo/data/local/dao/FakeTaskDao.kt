package com.cabovianco.todo.data.local.dao

import com.cabovianco.todo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskDao : TaskDao {
    private val tasks = mutableListOf<TaskEntity>()

    override suspend fun insert(task: TaskEntity) {
        tasks.add(task)
    }

    override suspend fun update(task: TaskEntity) {
        tasks.forEachIndexed { index, t ->
            if (t.id == task.id) {
                tasks[index] = task
            }
        }
    }

    override fun getAll(): Flow<List<TaskEntity>> = flow {
        emit(tasks.toList())
    }

    override fun getById(id: Int): Flow<TaskEntity> = flow {
        emit(tasks.first { it.id == id })
    }

    override suspend fun delete(task: TaskEntity) {
        tasks.removeIf { it.id == task.id }
    }
}
