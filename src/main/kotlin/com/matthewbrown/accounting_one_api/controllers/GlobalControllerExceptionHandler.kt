package com.matthewbrown.accounting_one_api.controllers

import com.matthewbrown.accounting_one_api.dtos.BadRequestResponse
import com.matthewbrown.accounting_one_api.exceptions.ConflictValidationException
import com.matthewbrown.accounting_one_api.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<BadRequestResponse> {
        val fields: MutableMap<String, String> = mutableMapOf()
        e.bindingResult.fieldErrors.forEach {
            fields += it.field to (it.defaultMessage ?: "unknown error")
        }
        return ResponseEntity.badRequest().body(BadRequestResponse(fields))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleMethodArgumentNotValidException(e: NotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(ConflictValidationException::class)
    fun handleConflictValidationException(e: ConflictValidationException): ResponseEntity<BadRequestResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BadRequestResponse(e.errors))
    }

    @ExceptionHandler(Throwable::class)
    fun handleException(e: Throwable): ResponseEntity<String> {
        // todo: log the exception
        println(e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred")
    }
}
