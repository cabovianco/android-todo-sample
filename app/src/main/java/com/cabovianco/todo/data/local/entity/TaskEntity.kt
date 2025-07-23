package com.cabovianco.todo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cabovianco.todo.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val done: Boolean
)

fun TaskEntity.toDomain() = Task(id, name, done)
