package com.diocorrea.infrastructure.adapters.input.rest.data.response

import java.time.ZonedDateTime
import java.util.UUID

data class TaskOutput(val name: String, val uuid: UUID, val created: ZonedDateTime)
