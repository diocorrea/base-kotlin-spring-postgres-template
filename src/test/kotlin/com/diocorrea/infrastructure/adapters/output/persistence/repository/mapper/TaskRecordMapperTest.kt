package com.diocorrea.infrastructure.adapters.output.persistence.repository.mapper

import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.db.generated.tables.records.TaskRecord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.UUID
import java.util.stream.Stream

internal class TaskRecordMapperTest {

    companion object {
        val defaultTime = ZonedDateTime.of(LocalDate.now(), LocalTime.NOON, ZoneOffset.UTC)

        @JvmStatic
        fun taskRecordInputs() = Stream.of(
            Arguments.of(null, "test123", defaultTime),
            Arguments.of(UUID.randomUUID(), "test123", defaultTime),
            Arguments.of(UUID.randomUUID(), "test123", null)
        )
    }

    @ParameterizedTest
    @MethodSource("taskRecordInputs")
    fun `should convert Task to TaskRecord`(uuid: UUID?, name: String, created: ZonedDateTime?) {
        val task = Task(uuid = uuid, name = name, created = created)
        val taskRecord = TaskRecord()
        taskRecord.id = uuid
        taskRecord.name = name
        taskRecord.created = created?.toOffsetDateTime()
        assertEquals(task, TaskRecordMapper.asTask(taskRecord))
        assertEquals(taskRecord, TaskRecordMapper.asRecord(task))
    }
}
