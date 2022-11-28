package com.diocorrea.domain.model

import java.time.ZonedDateTime
import java.util.UUID

data class Task(val name: String?, val uuid: UUID? = null, val created: ZonedDateTime? = null)
