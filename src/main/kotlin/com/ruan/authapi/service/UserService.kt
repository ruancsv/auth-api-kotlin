package com.ruan.authapi.service

import com.ruan.authapi.model.User
import com.ruan.authapi.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import com.ruan.authapi.exception.InvalidCredentialsException

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun createUser(email: String, password: String): User {
        val existingUser = userRepository.findByEmail(email)

        if (existingUser != null) {
            throw IllegalArgumentException("E-mail já cadastrado")
        }

        val hashedPassword = passwordEncoder.encode(password)
                ?: throw IllegalStateException("Não foi possível gerar o hash da senha")

        val user = User(
                email = email,
                password = hashedPassword
        )

        return userRepository.save(user)
    }

    fun authenticate(email: String, password: String): User {
        val user = userRepository.findByEmail(email)
                ?: throw InvalidCredentialsException()

        val passwordMatches = passwordEncoder.matches(
                password,
                user.password
        )

        if (!passwordMatches) {
            throw InvalidCredentialsException()
        }

        return user
    }
}