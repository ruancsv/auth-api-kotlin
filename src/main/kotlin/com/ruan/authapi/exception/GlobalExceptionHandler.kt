package com.ruan.authapi.exception

import com.ruan.authapi.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
            exception: IllegalArgumentException
    ): ResponseEntity<ErrorResponse> {

        val body = ErrorResponse(
                status = HttpStatus.CONFLICT.value(),
                message = exception.message ?: "Erro na requisição"
        )

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(
            exception: InvalidCredentialsException
    ): ResponseEntity<ErrorResponse> {

        val body = ErrorResponse(
                status = HttpStatus.UNAUTHORIZED.value(),
                message = exception.message ?: "E-mail ou senha inválidos"
        )

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
            exception: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {

        val errors = exception.bindingResult
                .fieldErrors
                .associate { error ->
                    error.field to (error.defaultMessage ?: "Valor inválido")
                }

        val body = ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = "Dados inválidos",
                errors = errors
        )

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body)
    }
    @ExceptionHandler(
            NoHandlerFoundException::class,
            NoResourceFoundException::class
    )
    fun handleNotFoundException(
            exception: Exception
    ): ResponseEntity<ErrorResponse> {

        val body = ErrorResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = "Recurso não encontrado"
        )

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body)
    }
}