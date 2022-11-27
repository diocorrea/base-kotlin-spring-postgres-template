package com.diocorrea.infrastructure.adapters.input.rest.exception

import com.diocorrea.domain.exception.TaskValidationException
import com.diocorrea.infrastructure.adapters.input.rest.data.response.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandlerAdvice {

    @ExceptionHandler(TaskValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(
        exception: TaskValidationException
    ): ResponseEntity<ErrorMessage> {
        return ResponseEntity.badRequest().body(ErrorMessage(exception.message!!))
    }
}
