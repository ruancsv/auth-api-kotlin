package com.ruan.authapi.controller

import com.ruan.authapi.security.SecurityConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UserController::class)
@Import(SecurityConfig::class)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var jwtDecoder: JwtDecoder

    @Test
    fun `deve retornar 401 ao acessar me sem token`() {

        mockMvc.get("/api/me")
                .andExpect {
                    status { isUnauthorized() }
                }
    }
    @Test
    fun `deve retornar email ao acessar me com token valido`() {

        mockMvc.get("/api/me") {
            with(
                    jwt().jwt { token ->
                        token.subject("teste@email.com")
                    }
            )
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.email") {
                        value("teste@email.com")
                    }
                }
    }
}