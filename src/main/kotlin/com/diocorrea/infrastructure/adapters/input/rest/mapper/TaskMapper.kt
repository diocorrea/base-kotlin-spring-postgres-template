package com.diocorrea.infrastructure.adapters.input.rest.mapper

import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput

interface TaskMapper {

    companion object {

        fun toTask(taskInput: TaskInput): Task {
            return Task(
                if (taskInput.name != null) {
                    taskInput.name!!
                } else {
                    throw TaskValidationException(
                        TaskValidationExceptionMessages.NULL_NAME_TASK
                    )
                }
            )
        }

        fun toTaskOutput(task: Task): TaskOutput {
            if (task.created == null || task.uuid == null) {
                throw IllegalArgumentException("Task must be complete to be converted to output")
            }
            return TaskOutput(name = task.name, uuid = task.uuid, created = task.created)
        }
    }
}
