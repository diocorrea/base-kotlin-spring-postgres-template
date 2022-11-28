package com.diocorrea.infrastructure.adapters.config

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.application.service.TaskService
import com.diocorrea.infrastructure.adapters.output.persistence.repository.TaskRepositoryDao
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Suppress("unused")
@Configuration
class BeanConfiguration {

    @Bean
    fun taskServiceSearchUseCase(

        taskRepository: TaskRepository
    ): TaskSearchUseCase {
        return TaskService(taskRepository = taskRepository)
    }

    @Bean
    fun taskStoreUseCase(

        taskRepository: TaskRepository
    ): TaskStoreUseCase {
        return TaskService(taskRepository = taskRepository)
    }

    @Bean
    fun taskRepository(dslContext: DSLContext): TaskRepository {
        return TaskRepositoryDao(dslContext)
    }
}
