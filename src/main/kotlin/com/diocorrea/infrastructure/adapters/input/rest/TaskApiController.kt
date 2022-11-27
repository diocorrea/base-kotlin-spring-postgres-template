package com.diocorrea.infrastructure.adapters.input.rest

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskListOutput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput
import com.diocorrea.infrastructure.adapters.input.rest.mapper.TaskMapper.Companion.toTask
import com.diocorrea.infrastructure.adapters.input.rest.mapper.TaskMapper.Companion.toTaskOutput
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/task")
class TaskApiController(
    val taskSearchUseCase: TaskSearchUseCase,
    val taskStoreUseCase: TaskStoreUseCase,
) {

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getAllTasks(): ResponseEntity<TaskListOutput> {
        return ResponseEntity.ok().body(TaskListOutput(taskSearchUseCase.findAllTasks().map { toTaskOutput(it) }))
    }

    @GetMapping(path = ["{taskId}"], produces = [APPLICATION_JSON_VALUE])
    fun getTaskById(@PathVariable taskId: UUID): ResponseEntity<TaskOutput> {
        val taskOptional = taskSearchUseCase.findTaskById(taskId)
        return taskOptional.map { ResponseEntity.ok().body(toTaskOutput(it)) }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun storeTask(@RequestBody taskInput: TaskInput): ResponseEntity<TaskOutput> {
        val storedTask = taskStoreUseCase.storeTask(toTask(taskInput = taskInput))
        return ResponseEntity.ok().body(toTaskOutput(storedTask))

    }
}
