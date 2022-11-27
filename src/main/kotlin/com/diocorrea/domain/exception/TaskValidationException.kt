package com.diocorrea.domain.exception

class TaskValidationException(message: TaskValidationExceptionMessages) : RuntimeException(message.getMessage())

enum class TaskValidationExceptionMessages {
    NULL_TASK {
        override fun getMessage() = "Task is null"
    },
    NULL_NAME_TASK {
        override fun getMessage() = "Task has a null name"
    },
    NAME_TOO_SHORT_TASK {
        override fun getMessage() = "Task has a name with less than 5 characters"
    },
    NAME_TOO_LONG_TASK {
        override fun getMessage() = "Task has a name with more than 30 characters"
    };

    abstract fun getMessage(): String
}
