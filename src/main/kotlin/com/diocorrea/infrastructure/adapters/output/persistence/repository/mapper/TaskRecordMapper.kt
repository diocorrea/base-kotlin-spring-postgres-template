package com.diocorrea.infrastructure.adapters.output.persistence.repository.mapper

import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.db.generated.tables.records.TaskRecord

class TaskRecordMapper {
    companion object {
        fun asTask(taskRecord: TaskRecord): Task {
            return Task(name = taskRecord.name, uuid = taskRecord.id, taskRecord.created?.toZonedDateTime())
        }

        fun asRecord(task: Task): TaskRecord {
            return TaskRecord(task.uuid, task.name, task.created?.toOffsetDateTime())
        }
    }
}
