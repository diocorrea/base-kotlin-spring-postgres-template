package com.diocorrea.infrastructure.adapters.input.rest.data.response

data class TaskListOutput(var tasks: List<TaskOutput>, var page: Int, var pageSize: Int)
