package com.diocorrea.domain.model

import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import java.time.ZonedDateTime
import java.util.UUID

data class Task(val name: String, val uuid: UUID? = null, val created: ZonedDateTime? = null) {
    init {
        if (name.length < 5) {
            throw TaskValidationException(TaskValidationExceptionMessages.NAME_TOO_SHORT_TASK)
        }
        if (name.length > 30) {
            throw TaskValidationException(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK)
        }
    }
}
