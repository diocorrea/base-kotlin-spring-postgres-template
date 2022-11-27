package com.diocorrea.infrastructure.adapters.input.rest

import com.diocorrea.AbstractIntegrationTest
import com.diocorrea.infrastructure.adapters.input.rest.data.request.TaskInput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskListOutput
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.ZonedDateTime
import java.util.UUID

class TaskApiControllerIntegrationTest : AbstractIntegrationTest() {

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    @LocalServerPort
    lateinit var port: Integer

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should return 404 for a non-existing task`() {
        assertEquals(
            HttpStatus.NOT_FOUND,
            /* actual = */ restTemplate.getForEntity(
                /* url = */ "http://localhost:$port/task/{taskid}",
                /* responseType = */ String::class.java,
                /* ...urlVariables = */ UUID.randomUUID()
            ).statusCode
        )
    }

    @Test
    fun `should store task correctly post endpoint`() {
        val taskInput = TaskInput(name = "smart.name.super")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val response =
            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", taskInput, TaskOutput::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)

        assertTrue(ZonedDateTime.now().isAfter(response.body!!.created))
        assertNotNull(response.body!!.uuid)
        assertEquals("smart.name.super", response.body!!.name)
    }

    @Test
    fun `should return task after inserting it`() {
        val taskInput = TaskInput(name = "smart.name.super")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val insertedTask =
            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", taskInput, TaskOutput::class.java)
        val body = insertedTask.body
        assertNotNull(body)

        val selectedTask = restTemplate.getForEntity(
            "http://localhost:$port/task/{taskid}",
            TaskOutput::class.java,
            body!!.uuid
        )

        assertEquals(insertedTask, selectedTask)
    }

    @Test
    fun `should get all All tasks`() {
        val taskInputs = listOf(
            TaskInput(name = "smart.name.super"),
            TaskInput(name = "smart.name.super.1"),
            TaskInput(name = "smart.name.super.2")
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        taskInputs.forEach {
            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", it, TaskOutput::class.java)
        }

        val response =
            restTemplate.getForEntity(/* url = */ "http://localhost:$port/task", TaskListOutput::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(3, response.body!!.tasks.size)

        val stringList = response.body!!.tasks.map { it.name }

        taskInputs.map { it.name }.forEach {
            assertTrue(stringList.contains(it))
        }
    }
}
