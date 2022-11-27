package com.diocorrea.domain.service

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task
import java.util.Optional
import java.util.UUID

class TaskService(
    private val taskValidationService: TaskValidationService,
    private val taskRepository: TaskRepository,
) :
    TaskSearchUseCase, TaskStoreUseCase {

    override fun storeTask(task: Task?): Task {
        taskValidationService.validate(task)
        return taskRepository.create(task!!)
    }

    override fun findAllTasks(): List<Task> {
        return taskRepository.selectAllTasks()
    }

    override fun findTaskById(uuid: UUID): Optional<Task> {
        return taskRepository.selectTaskById(uuid)
    }
}
