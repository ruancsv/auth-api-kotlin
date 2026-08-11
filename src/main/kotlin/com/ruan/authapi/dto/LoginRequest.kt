package com.ruan.authapi.dto

data class LoginRequest(
        val email: String,
        val password: String
)