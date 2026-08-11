package com.ruan.authapi.service

import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class JwtService(
        private val jwtEncoder: JwtEncoder
) {

    fun generateToken(email: String): String {
        val now = Instant.now()

        val claims = JwtClaimsSet.builder()
                .issuer("auth-api")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(email)
                .build()

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .tokenValue
    }
}