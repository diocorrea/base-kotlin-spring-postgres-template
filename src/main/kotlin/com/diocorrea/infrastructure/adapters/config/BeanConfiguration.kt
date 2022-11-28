package com.diocorrea.infrastructure.adapters.config

import com.diocorrea.application.ports.input.TaskSearchUseCase
import com.diocorrea.application.ports.input.TaskStoreUseCase
import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.application.service.TaskService
import com.diocorrea.application.service.TaskValidationService
import com.diocorrea.infrastructure.adapters.output.persistence.repository.TaskRepositoryDao
import org.jooq.DSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun taskServiceSearchUseCase(taskValidationService: TaskValidationService, taskRepository: TaskRepository): TaskSearchUseCase {
        return TaskService(taskValidationService = taskValidationService, taskRepository = taskRepository)
    }

    @Bean
    fun taskStoreUseCase(taskValidationService: TaskValidationService, taskRepository: TaskRepository): TaskStoreUseCase {
        return TaskService(taskValidationService = taskValidationService, taskRepository = taskRepository)
    }

    @Bean
    fun taskValidationService(): TaskValidationService {
        return TaskValidationService()
    }

    @Bean
    fun taskRepository(dslContext: DSLContext): TaskRepository {
        return TaskRepositoryDao(dslContext)
    }
}
