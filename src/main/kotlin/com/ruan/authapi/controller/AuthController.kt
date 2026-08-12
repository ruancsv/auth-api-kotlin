package com.ruan.authapi.controller

import com.ruan.authapi.dto.LoginRequest
import com.ruan.authapi.dto.LoginResponse
import com.ruan.authapi.dto.RegisterRequest
import com.ruan.authapi.dto.UserResponse
import com.ruan.authapi.service.JwtService
import com.ruan.authapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
        private val userService: UserService,
        private val jwtService: JwtService
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
            @Valid @RequestBody request: RegisterRequest
    ): UserResponse {

        val user = userService.createUser(
                email = request.email,
                password = request.password
        )

        return UserResponse(
                id = user.id,
                email = user.email
        )
    }

    @PostMapping("/login")
    fun login(
            @Valid @RequestBody request: LoginRequest
    ): LoginResponse {

        val user = userService.authenticate(
                email = request.email,
                password = request.password
        )

        val token = jwtService.generateToken(user.email)

        return LoginResponse(
                token = token
        )
    }
}