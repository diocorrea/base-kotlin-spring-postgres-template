package com.diocorrea

import org.junit.ClassRule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AbstractIntegrationTest {

    companion object {
        @ClassRule
        @JvmStatic
        val postgresSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15.1")
            .withDatabaseName("postgres")
            .withExposedPorts(5432)
            .withUsername("user")
            .withPassword("pass")

        @JvmStatic
        @ClassRule
        @DynamicPropertySource
        fun init(registry: DynamicPropertyRegistry) {
            postgresSQLContainer.start()
            registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl)
        }

        @AfterAll
        @JvmStatic
        fun destroy() {
            postgresSQLContainer.stop()
        }
    }
}
