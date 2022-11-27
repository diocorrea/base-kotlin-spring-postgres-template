package com.diocorrea.infrastructure.adapters.output.persistence.repository

import com.diocorrea.AbstractIntegrationTest
import com.diocorrea.application.ports.ouput.TaskRepository
import com.diocorrea.domain.model.Task
import com.diocorrea.infrastructure.adapters.db.generated.Tables
import org.jooq.DSLContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.ZonedDateTime

class TaskRepositoryIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Autowired
    lateinit var dsl: DSLContext

    @Test
    fun `test simple select`() {
        taskRepository.create(
            Task("Dio")
        )
        taskRepository.create(
            Task("Dio2")
        )

        assertEquals(2, taskRepository.selectAllTasks().size)
    }

    @Test
    fun `test select by id`() {
        val task = taskRepository.create(
            Task("Dio")
        )

        val selectedTaskById = taskRepository.selectTaskById(task.uuid!!)

        assertTrue(selectedTaskById.isPresent)
        assertEquals(
            task.toString(),
            selectedTaskById.get().toString()
        )
    }

    @Test
    fun `save task into database`() {
        val task = Task("Dio")

        val inserted = taskRepository.create(task)

        Assertions.assertNotNull(inserted.uuid)
        Assertions.assertNotNull(inserted.created)

        assertEquals(task.name, inserted.name)
        assertTrue(ZonedDateTime.now().isAfter(inserted.created))
    }

    @Test
    fun `should return the same task if inserting it twice`() {
        val task = taskRepository.create(
            Task("Dio")
        )
        val task2 = taskRepository.create(task)

        assertEquals(task.uuid, task2.uuid)
    }

    @AfterEach
    fun cleanUpDb() {
        dsl.deleteFrom(Tables.TASK).execute()
    }
}
