package com.diocorrea.domain.service

import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.domain.exception.TaskValidationExceptionMessages
import com.diocorrea.domain.model.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TaskServiceTest {

    lateinit var useCaseUnderTest: TaskService

    lateinit var mockTaskValidationService: TaskValidationService

    lateinit var mockTaskRepository: TaskRepository

    @BeforeEach
    fun init() {
        mockTaskValidationService = mockk()
        mockTaskRepository = mockk()
        useCaseUnderTest = TaskService(mockTaskValidationService, mockTaskRepository)
    }

    @Test
    fun `should call validation and repository to store Task`() {
        val task = Task("input Task")
        val returnTask = Task("return Task")
        every { mockTaskValidationService.validate(task) }.answers { true }
        every { mockTaskRepository.store(task) }.answers { returnTask }

        Assertions.assertEquals(returnTask, useCaseUnderTest.storeTask(task))

        verify(exactly = 1) { mockTaskValidationService.validate(task) }
        verify(exactly = 1) { mockTaskRepository.store(task) }
    }

    @Test
    fun `shouldn't call repository if validation fails`() {
        val task = Task("input Task")
        every { mockTaskValidationService.validate(task) }.throws(TaskValidationException(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK))

        assertThrows<TaskValidationException>(TaskValidationExceptionMessages.NAME_TOO_LONG_TASK.getMessage()) { useCaseUnderTest.storeTask(task) }

        verify(exactly = 1) { mockTaskValidationService.validate(task) }
        verify(exactly = 0) { mockTaskRepository.store(task) }
    }
}
