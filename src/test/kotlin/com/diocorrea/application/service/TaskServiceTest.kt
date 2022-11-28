package com.diocorrea.application.service

import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class TaskServiceTest {

    lateinit var useCaseUnderTest: TaskService

    lateinit var mockTaskRepository: TaskRepository

    @BeforeEach
    fun init() {
        mockTaskRepository = mockk()
        useCaseUnderTest = TaskService(mockTaskRepository)
    }

    @Test
    fun `should call validation and repository to store Task`() {
        val task = Task("input Task")
        val returnTask = Task("return Task")

        every { mockTaskRepository.create(task) }.answers { returnTask }

        Assertions.assertEquals(returnTask, useCaseUnderTest.storeTask(task))

        verify(exactly = 1) { mockTaskRepository.create(task) }
    }

    @Test
    fun `should call repository to find all Tasks`() {
        val returnTask = Task("return Task")

        every { mockTaskRepository.selectAllTasks() }.answers { listOf(returnTask) }

        Assertions.assertEquals(1, useCaseUnderTest.findAllTasks().size)
        Assertions.assertEquals(returnTask, useCaseUnderTest.findAllTasks()[0])
    }

    @Test
    fun `should call repository to find task By id`() {
        val uuid = UUID.randomUUID()
        val returnTask = Task("return Task", uuid)

        every { mockTaskRepository.selectAllTasks() }.answers { listOf(returnTask) }

        Assertions.assertEquals(1, useCaseUnderTest.findAllTasks().size)
        Assertions.assertEquals(uuid, useCaseUnderTest.findAllTasks()[0].uuid)
    }
}
