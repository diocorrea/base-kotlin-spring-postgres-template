package com.diocorrea.infrastructure.adapters.input.rest

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskListOutput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskApiController(val taskSearchUseCase: TaskSearchUseCase, val taskStoreUseCase: TaskStoreUseCase) {

    @GetMapping
    fun getAllTasks(): TaskListOutput {
        return taskSearchUseCase.findAllTasks()
    }
}
