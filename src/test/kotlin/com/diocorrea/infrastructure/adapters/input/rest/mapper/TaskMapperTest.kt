package com.diocorrea.infrastructure.adapters.input.rest.mapper

import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput
import com.diocorrea.infrastructure.adapters.input.rest.mapper.TaskMapper.Companion.toTask
import com.diocorrea.infrastructure.adapters.input.rest.mapper.TaskMapper.Companion.toTaskOutput
import org.junit.jupiter.api.Assertions.assertEquals
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
        val taskInput = TaskInput("dio123")
        val task = Task("dio123")

        assertEquals(task, toTask(taskInput))
    }

    @Test
    fun `should convert Task to TaskOutput`() {
        val randomUUID = UUID.randomUUID()
        val zonedDateTime = ZonedDateTime.now()
        val name = "dio123"
        val task = Task(name = name, uuid = randomUUID, created = zonedDateTime)

        val taskOutput = TaskOutput(name = name, uuid = randomUUID, created = zonedDateTime)

        assertEquals(taskOutput, toTaskOutput(task))
    }

    @ParameterizedTest
    @MethodSource("multipleInputs")
    fun `should throw exception if any parameter is null`(
        name: String,
        randomUUID: UUID?,
        zonedDateTime: ZonedDateTime?
    ) {
        val task = Task(name = name, uuid = randomUUID, created = zonedDateTime)

        assertThrows<IllegalArgumentException> {
            toTaskOutput(task)
        }
    }

    companion object {
        @JvmStatic
        fun multipleInputs() = Stream.of(
            Arguments.of("Dio123", null, ZonedDateTime.now()),
            Arguments.of("Dio123", UUID.randomUUID(), null)
        )
    }
}
