package com.diocorrea.application.ports.input

import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskListOutput

interface TaskSearchUseCase {
    fun findAllTasks(): TaskListOutput
}
