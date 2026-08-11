package com.ruan.authapi.exception

class InvalidCredentialsException(
        message: String = "E-mail ou senha inválidos"
) : RuntimeException(message)