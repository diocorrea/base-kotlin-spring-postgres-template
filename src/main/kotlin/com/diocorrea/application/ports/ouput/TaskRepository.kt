package com.diocorrea.application.ports.ouput

import com.diocorrea.domain.model.Task

interface TaskRepository {
    fun store(task: Task): Task
}
