package com.diocorrea.infrastructure.adapters.output.persistence.repository

import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task

class TaskRepositoryDao : TaskRepository {
    override fun store(task: Task): Task {
        TODO("Not yet implemented")
    }
}
