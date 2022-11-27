package com.diocorrea.infrastructure.adapters.output.persistence.repository

import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.db.generated.Tables.TASK
import com.diocorrea.infrastructure.adapters.db.generated.tables.records.TaskRecord
import com.diocorrea.infrastructure.adapters.output.persistence.repository.mapper.TaskRecordMapper.Companion.asRecord
import com.diocorrea.infrastructure.adapters.output.persistence.repository.mapper.TaskRecordMapper.Companion.asTask
import org.jooq.DSLContext
import java.time.OffsetDateTime
import java.util.Optional
import java.util.UUID

class TaskRepositoryDao(private val dsl: DSLContext) : TaskRepository {

    override fun selectAllTasks(): List<Task> {
        return dsl.selectFrom(TASK).stream().map { asTask(it) }.toList()
    }

    override fun selectTaskById(taskId: UUID): Optional<Task> {
        return dsl.selectFrom(TASK)
            .where(TASK.ID.eq(taskId))
            .fetch().stream()
            .map { asTask(it) }
            .findFirst()
    }

    override fun create(task: Task): Task {
        val taskRecord: TaskRecord = asRecord(task)
        if (task.uuid == null) {
            taskRecord.id = UUID.randomUUID()
        } else {
            return selectTaskById(taskRecord.id).orElseThrow()
        }
        taskRecord.created = OffsetDateTime.now()
        dsl.insertInto(TASK).set(taskRecord).execute()
        return selectTaskById(taskRecord.id).orElseThrow()
    }

}
