package com.diocorrea.infrastructure.adapters.input.rest.mapper

import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.ZonedDateTime
import java.util.UUID
import java.util.stream.Stream

internal class TaskMapperTest {

    @Test
    fun `should convert TaskInput into Task`() {
        val taskInput = TaskInput("dio")
        val task = Task("dio")

        assertEquals(task, TaskMapper().toTask(taskInput))
    }

    @Test
    fun `should convert Task to TaskOutput`() {
        val randomUUID = UUID.randomUUID()
        val zonedDateTime = ZonedDateTime.now()
        val name = "dio"
        val task = Task(name = name, uuid = randomUUID, created = zonedDateTime)

        val taskOutput = TaskOutput(name = name, uuid = randomUUID, created = zonedDateTime)

        assertEquals(taskOutput, TaskMapper().toTaskOutput(task))
    }

    @ParameterizedTest
    @MethodSource("multipleInputs")
    fun `should throw exception if any parameter is null`(
        name: String?,
        randomUUID: UUID?,
        zonedDateTime: ZonedDateTime?
    ) {
        val task = Task(name = name, uuid = randomUUID, created = zonedDateTime)

        assertThrows<IllegalArgumentException> {
            TaskMapper().toTaskOutput(task)
        }
    }

    companion object {
        @JvmStatic
        fun multipleInputs() = Stream.of(
            Arguments.of(null, UUID.randomUUID(), ZonedDateTime.now()),
            Arguments.of("Dio", null, ZonedDateTime.now()),
            Arguments.of("Dio", UUID.randomUUID(), null)
        )
    }
}
