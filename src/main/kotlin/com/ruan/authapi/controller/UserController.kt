package com.ruan.authapi.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/me")
    fun me(authentication: Authentication): Map<String, String> {
        return mapOf(
                "email" to authentication.name
        )
    }
}