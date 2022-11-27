package com.diocorrea.application.ports.ouput

import com.diocorrea.domain.model.Task
import java.util.Optional
import java.util.UUID

interface TaskRepository {
    fun create(task: Task): Task
    fun selectAllTasks(): List<Task>
    fun selectTaskById(taskId: UUID): Optional<Task>
}
