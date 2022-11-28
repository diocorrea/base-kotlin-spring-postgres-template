package com.diocorrea.application.ports.input

import com.diocorrea.domain.model.Task

interface TaskStoreUseCase {
    fun storeTask(task: Task): Task
}
