package com.diocorrea.infrastructure.adapters.input.rest.mapper

import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput

interface TaskMapper {

    companion object {

        fun toTask(taskInput: TaskInput): Task? {
            if (taskInput.name == null) return null
            return Task(taskInput.name)
        }

        fun toTaskOutput(task: Task): TaskOutput {
            if (task.name == null || task.created == null || task.uuid == null) {
                throw IllegalArgumentException("Task must be complete to be converted to output")
            }
            return TaskOutput(name = task.name, uuid = task.uuid, created = task.created)
        }
    }
}
