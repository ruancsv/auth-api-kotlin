package com.ruan.authapi.dto

data class RegisterRequest(
        val email: String,
        val password: String
)