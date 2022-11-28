package com.diocorrea.domain.service

import com.diocorrea.application.service.TaskValidationService
import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import com.diocorrea.domain.model.Task
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

internal class TaskValidationServiceTest {

    private val useCaseUnderTest = TaskValidationService()

    @Test
    fun `should validate null Task`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NULL_TASK.getMessage()) {
            useCaseUnderTest.validate(
                null
            )
        }
    }

    @Test
    fun `should validate null Task name`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NULL_NAME_TASK.getMessage()) {
            useCaseUnderTest.validate(
                Task(null)
            )
        }
    }

    @Test
    fun `should validate Task with name too short`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NAME_TOO_SHORT_TASK.getMessage()) {
            useCaseUnderTest.validate(
                Task(UUID.randomUUID().toString().substring(0, 4))
            )
        }
    }

    @Test
    fun `should validate Task with name too long`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK.getMessage()) {
            useCaseUnderTest.validate(
                Task("tZJxag42aZO^4I1dM^Wud7kM!gPNhRp")
            )
        }
    }
}
