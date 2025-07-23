package com.cabovianco.todo.domain.usecase

import com.cabovianco.todo.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(id: Int) =
        taskRepository.getTaskById(id)
}
