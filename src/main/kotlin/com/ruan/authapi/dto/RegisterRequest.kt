package com.ruan.authapi.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(

        @field:NotBlank(message = "Email é obrigatório")
        @field:Email(message = "Email inválido")
        val email: String,

        @field:NotBlank(message = "Senha é obrigatória")
        @field:Size(
                min = 8,
                message = "A senha deve ter no mínimo 8 caracteres"
        )
        val password: String
)