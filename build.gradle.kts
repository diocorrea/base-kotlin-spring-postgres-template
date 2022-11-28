import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging


plugins {
    // jooq generator
    id("java")
    id("nu.studer.jooq") version "8.0"
    // spring
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    // sonar
    id("org.sonarqube") version "3.5.0.2730"
    // kotlin
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    jacoco
}

group = "com.diocorrea"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["testcontainersVersion"] = "1.17.6"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.mockk:mockk:1.13.2")

    jooqGenerator("org.postgresql:postgresql:42.3.5")
}

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    version.set("3.17.5") // default (can be omitted)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS) // default (can be omitted)

    configurations {
        create("main") { // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false) // default (can be omitted)

            jooqConfiguration.apply {
                logging = Logging.ERROR
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5431/postgres"
                    user = "user"
                    password = "pass"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    target.apply {
                        packageName = "com.diocorrea.infrastructure.adapters.db.generated"
                        directory =
                            "src/main/java/com/diocorrea/infrastructure/adapters/db/generated" // default (can be omitted)
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    configure<JacocoTaskExtension> {
        includes = listOf("com.diocorrea.*")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude("**/generated/**")
                }
            }
        )
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.9".toBigDecimal()
            }
            isEnabled = true
            element = "CLASS"
            includes = listOf("com.diocorrea.*")
        }

        rule {
            isEnabled = true
            element = "CLASS"
            includes = listOf("com.diocorrea.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

sonarqube {
    properties {
        property("sonar.sources", "src/main/kotlin")
        property("sonar.includes", "*.kt")
        property("sonar.tests", "src/test/kotlin")
        property("sonar.scm.disabled", "true")
    }
}
