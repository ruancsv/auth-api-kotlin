package com.ruan.authapi.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authorizeHttpRequests {
                    it
                            .requestMatchers(
                                    "/auth/register",
                                    "/auth/login"
                            ).permitAll()
                            .anyRequest().authenticated()
                }
                .oauth2ResourceServer {
                    it.jwt { }
                }

        return http.build()
    }
}