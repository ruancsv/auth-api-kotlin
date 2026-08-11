package com.ruan.authapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig(
        @Value("\${jwt.secret}")
        private val jwtSecret: String
) {

    private fun secretKey(): SecretKey {
        val decodedKey = Base64.getDecoder().decode(jwtSecret)

        return SecretKeySpec(
                decodedKey,
                "HmacSHA256"
        )
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder
                .withSecretKey(secretKey())
                .macAlgorithm(MacAlgorithm.HS256)
                .build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        return NimbusJwtEncoder
                .withSecretKey(secretKey())
                .algorithm(MacAlgorithm.HS256)
                .build()
    }
}