package com.diocorrea.domain.service

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskListOutput

class TaskService(private val taskValidationService: TaskValidationService, private val taskRepository: TaskRepository) :
    TaskSearchUseCase, TaskStoreUseCase {

    override fun storeTask(task: Task?): Task {
        taskValidationService.validate(task)
        return taskRepository.store(task!!)
    }

    override fun findAllTasks(): TaskListOutput {
        TODO("Not yet implemented")
    }
}
