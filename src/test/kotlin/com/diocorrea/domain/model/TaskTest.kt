package com.diocorrea.domain.model

import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class TaskTest {

    @Test
    fun `should validate Task with name too short`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NAME_TOO_SHORT_TASK.getMessage()) {
            Task(UUID.randomUUID().toString().substring(0, 4))
        }
    }

    @Test
    fun `should validate Task with name too long`() {
        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK.getMessage()) {
            Task("tZJxag42aZO^4I1dM^Wud7kM!gPNhRp")
        }
    }
}
