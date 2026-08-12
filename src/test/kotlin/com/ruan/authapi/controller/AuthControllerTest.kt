package com.ruan.authapi.controller

import com.ruan.authapi.model.User
import com.ruan.authapi.service.JwtService
import com.ruan.authapi.service.UserService
import org.junit.jupiter.api.Test
import com.ruan.authapi.security.SecurityConfig
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthController::class)
@Import(SecurityConfig::class)
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var userService: UserService

    @MockitoBean
    lateinit var jwtDecoder: JwtDecoder

    @MockitoBean
    lateinit var jwtService: JwtService

    @Test
    fun `deve registrar usuario e retornar 201`() {

        val user = User(
                id = 1L,
                email = "teste@email.com",
                password = "senhaCriptografada"
        )

        given(
                userService.createUser(
                        email = "teste@email.com",
                        password = "12345678"
                )
        ).willReturn(user)

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
        {
            "email": "teste@email.com",
            "password": "12345678"
        }
    """.trimIndent()
        }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.email") {
                        value("teste@email.com")
                    }
                }
    }
    @Test
    fun `deve retornar 400 quando dados de registro forem invalidos`() {

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "email": "teste",
                "password": "123"
            }
        """.trimIndent()
        }
                .andExpect {
                    status { isBadRequest() }
                    jsonPath("$.status") {
                        value(400)
                    }
                    jsonPath("$.errors.email") {
                        value("Email inválido")
                    }
                    jsonPath("$.errors.password") {
                        value("A senha deve ter no mínimo 8 caracteres")
                    }
                }
    }
    @Test
    fun `deve realizar login e retornar token`() {

        val user = User(
                id = 1L,
                email = "teste@email.com",
                password = "senhaCriptografada"
        )

        given(
                userService.authenticate(
                        email = "teste@email.com",
                        password = "12345678"
                )
        ).willReturn(user)

        given(
                jwtService.generateToken("teste@email.com")
        ).willReturn("token-jwt-teste")

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "email": "teste@email.com",
                "password": "12345678"
            }
        """.trimIndent()
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.token") {
                        value("token-jwt-teste")
                    }
                }
    }
    @Test
    fun `deve retornar 401 quando credenciais forem invalidas`() {

        given(
                userService.authenticate(
                        email = "teste@email.com",
                        password = "senhaErrada"
                )
        ).willThrow(
                com.ruan.authapi.exception.InvalidCredentialsException(
                        "E-mail ou senha inválidos"
                )
        )

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "email": "teste@email.com",
                "password": "senhaErrada"
            }
        """.trimIndent()
        }
                .andExpect {
                    status { isUnauthorized() }
                    jsonPath("$.message") {
                        value("E-mail ou senha inválidos")
                    }
                }
    }
}