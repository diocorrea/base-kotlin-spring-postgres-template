package com.diocorrea.infrastructure.adapters.input.rest

import com.diocorrea.AbstractIntegrationTest
import com.diocorrea.infrastructure.adapters.input.rest.data.response.TaskOutput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import java.time.ZonedDateTime
import java.util.UUID

class TaskApiControllerIntegrationTest : AbstractIntegrationTest() {

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
        val parameters = LinkedMultiValueMap<String, Any>()
        parameters.add("name", "Dio")

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)

        val response =
            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, TaskOutput::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)

        assertTrue(ZonedDateTime.now().isAfter(response.body!!.created))
        assertNotNull(response.body!!.uuid)
        assertEquals("Dio", response.body!!.name)
    }

//    @Test
//    fun `should return the same task if tried to insert it twice`() {
//        val parameters = LinkedMultiValueMap<String, Any>()
//        parameters.add("image", smallImage)
//        parameters.add("height", 10)
//        parameters.add("width", 100)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)
//
//        val response1 =
//            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)
//        val response2 = restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)
//
//        assertEquals(HttpStatus.OK, response1.statusCode)
//        assertNotNull(response1.body)
//        assertEquals(HttpStatus.OK, response2.statusCode)
//        assertNotNull(response2.body)
//
//        assertEquals(response2.body, response1.body)
//    }
//
//    @Test
//    fun `should return 400 if no image is provided`() {
//        val parameters = LinkedMultiValueMap<String, Any>()
//        parameters.add("height", 10)
//        parameters.add("width", 100)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)
//
//        val response =
//            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//    }
//
//    @Test
//    fun `should return 400 if height is over 2160`() {
//        val parameters = LinkedMultiValueMap<String, Any>()
//        parameters.add("image", smallImage)
//        parameters.add("height", 10)
//        parameters.add("width", 2161)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)
//
//        val response =
//            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//    }
//
//    @Test
//    fun `should return 400 if width is over 3840`() {
//        val parameters = LinkedMultiValueMap<String, Any>()
//        parameters.add("image", smallImage)
//        parameters.add("height", 3841)
//        parameters.add("width", 30)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)
//
//        val response =
//            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
//    }
//
//    @Test
//    fun `should get all All tasks`() {
//        val parameters = LinkedMultiValueMap<String, Any>()
//        parameters.add("image", smallImage)
//        parameters.add("height", 10)
//        parameters.add("width", 100)
//
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.MULTIPART_FORM_DATA
//        restTemplate.postForEntity(/* url = */
//            "http://localhost:$port/task",
//            HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers),
//            Task::class.java
//        )
//
//        val response =
//            restTemplate.getForEntity(/* url = */ "http://localhost:$port/taskList", TaskList::class.java)
//
//        assertEquals(HttpStatus.OK, response.statusCode)
//        assertNotNull(response.body)
//        assertEquals(1, response.body!!.tasks.size)
//    }
}
