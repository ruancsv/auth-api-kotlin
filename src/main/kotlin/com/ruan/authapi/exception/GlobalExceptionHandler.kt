package com.ruan.authapi.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
            exception: IllegalArgumentException
    ): ResponseEntity<Map<String, String>> {

        val body = mapOf(
                "message" to (exception.message ?: "Erro na requisição")
        )

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(
            exception: InvalidCredentialsException
    ): ResponseEntity<Map<String, String>> {

        val body = mapOf(
                "message" to (exception.message ?: "E-mail ou senha inválidos")
        )

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
            exception: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any>> {

        val errors = exception.bindingResult
                .fieldErrors
                .associate { error ->
                    error.field to (error.defaultMessage ?: "Valor inválido")
                }

        val body = mapOf(
                "status" to HttpStatus.BAD_REQUEST.value(),
                "errors" to errors
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body)
    }
}