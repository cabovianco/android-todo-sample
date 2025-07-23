package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() =
        taskRepository.getAllTasks()
}
