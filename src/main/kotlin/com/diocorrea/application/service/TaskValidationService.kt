package com.diocorrea.application.service

import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import com.diocorrea.domain.model.Task

class TaskValidationService {

    fun validate(task: Task?): Boolean {
        if (task == null) {
            throw TaskValidationException(TaskValidationExceptionMessages.NULL_TASK)
        }
        if (task.name == null) {
            throw TaskValidationException(TaskValidationExceptionMessages.NULL_NAME_TASK)
        }
        if (task.name.length < 5) {
            throw TaskValidationException(TaskValidationExceptionMessages.NAME_TOO_SHORT_TASK)
        }
        if (task.name.length > 30) {
            throw TaskValidationException(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK)
        }
        return true
    }
}
