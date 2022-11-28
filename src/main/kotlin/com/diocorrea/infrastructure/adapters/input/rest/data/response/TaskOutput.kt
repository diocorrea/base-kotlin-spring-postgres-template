package com.diocorrea.infrastructure.adapters.input.rest.data.response

import java.time.ZonedDateTime
import java.util.UUID

data class TaskOutput(var name: String, var uuid: UUID, var created: ZonedDateTime)
