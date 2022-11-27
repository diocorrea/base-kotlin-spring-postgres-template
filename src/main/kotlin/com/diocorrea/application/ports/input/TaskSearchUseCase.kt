package com.diocorrea.application.ports.input

import com.diocorrea.domain.model.Task
import java.util.Optional
import java.util.UUID

interface TaskSearchUseCase {
    fun findAllTasks(): List<Task>
    fun findTaskById(taskId: UUID): Optional<Task>
}
