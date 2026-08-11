package com.ruan.authapi.exception

import com.ruan.authapi.exception.InvalidCredentialsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
}