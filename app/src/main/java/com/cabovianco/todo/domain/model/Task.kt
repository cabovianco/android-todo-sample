package com.cabovianco.todo.domain.model

import com.cabovianco.todo.data.local.entity.TaskEntity

data class Task(
    val id: Int = 0,
    val name: String,
    val done: Boolean = false
)

fun Task.toEntity() = TaskEntity(id, name, done)
