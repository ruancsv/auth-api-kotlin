# Auth API Kotlin

API REST de autenticação desenvolvida com **Kotlin** e **Spring Boot**, utilizando **JWT** para autenticação e **BCrypt** para proteção de senhas.

O projeto foi criado com foco em estudo e prática de autenticação segura em aplicações backend.

---

## Tecnologias utilizadas

- Kotlin
- Spring Boot
- Spring Security
- JWT
- BCrypt
- Spring Data JPA
- Hibernate
- PostgreSQL
- Gradle
- Java 21

---

## Funcionalidades

- Cadastro de usuários
- Login com e-mail e senha
- Hash de senha utilizando BCrypt
- Validação de e-mail duplicado
- Tratamento de credenciais inválidas
- Geração de JWT
- Validação de JWT
- Proteção de endpoints
- Autenticação stateless
- Validação de dados de entrada com Jakarta Validation
- Testes da camada web com MockMvc

---

## Estrutura do projeto

```text
src/main/kotlin/com/ruan/authapi
│
├── config
│   ├── JwtConfig.kt
│   └── PasswordConfig.kt
│
├── controller
│   ├── AuthController.kt
│   └── UserController.kt
│
├── dto
│   ├── LoginRequest.kt
│   ├── LoginResponse.kt
│   ├── RegisterRequest.kt
│   └── UserResponse.kt
│
├── exception
│   ├── GlobalExceptionHandler.kt
│   └── InvalidCredentialsException.kt
│
├── model
│   └── User.kt
│
├── repository
│   └── UserRepository.kt
│
├── security
│   └── SecurityConfig.kt
│
└── service
    ├── JwtService.kt
    └── UserService.kt
```

---

## Endpoints

### Cadastro de usuário

```http
POST /auth/register
```

Exemplo de requisição:

```json
{
  "email": "user@example.com",
  "password": "12345678"
}
```

Exemplo de resposta:

```json
{
  "id": 1,
  "email": "user@example.com"
}
```

---

### Login

```http
POST /auth/login
```

Exemplo de requisição:

```json
{
  "email": "user@example.com",
  "password": "123456"
}
```

Exemplo de resposta:

```json
{
  "token": "<jwt-token>"
}
```

---

### Endpoint protegido

```http
GET /api/me
```

O JWT deve ser enviado no header:

```http
Authorization: Bearer <jwt-token>
```

Exemplo de resposta:

```json
{
  "email": "user@example.com"
}
```

Sem um token válido, o endpoint retorna:

```text
401 Unauthorized
```

---

## Autenticação

O fluxo de autenticação funciona da seguinte forma:

```text
Usuário
   ↓
POST /auth/login
   ↓
Validação de e-mail e senha
   ↓
BCrypt
   ↓
Credenciais válidas
   ↓
Geração do JWT
   ↓
Bearer Token
   ↓
Endpoints protegidos
```

---

## Segurança

As senhas não são armazenadas em texto puro.

O projeto utiliza **BCrypt** para gerar o hash das senhas antes de armazená-las no banco de dados.

A autenticação dos endpoints protegidos utiliza **JWT (JSON Web Token)**.

A aplicação é configurada como stateless, não mantendo sessão no servidor.

---

## Variáveis de ambiente

Informações sensíveis não são armazenadas diretamente no código-fonte.

A aplicação utiliza:

```text
DB_PASSWORD
JWT_SECRET
```

Exemplo do `application.properties`:

```properties
spring.application.name=auth-api

spring.datasource.url=jdbc:postgresql://localhost:5432/auth_api
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=${JWT_SECRET}
```

Nunca adicione senhas, tokens ou segredos reais ao repositório.

---

## Banco de dados

O projeto utiliza PostgreSQL.

Banco utilizado no ambiente de desenvolvimento:

```text
auth_api
```

A entidade principal atualmente é:

```text
User
```

com os campos:

```text
id
email
password
```

O campo `password` armazena apenas o hash BCrypt da senha.

---

## Executando o projeto

### Requisitos

- Java 21
- PostgreSQL
- Gradle Wrapper incluído no projeto

Antes de executar, configure as variáveis de ambiente necessárias.

No PowerShell:

```powershell
$env:DB_PASSWORD="sua_senha_local"
$env:JWT_SECRET="sua_chave_jwt_local"
```

Os valores acima são apenas exemplos. Não utilize credenciais reais em documentação ou commits.

Depois execute:

```powershell
.\gradlew bootRun
```

A aplicação será iniciada em:

```text
http://localhost:8080
```

---

## Build

Para compilar e executar os testes:

```powershell
.\gradlew build
```

---

## Testando a API

Os endpoints podem ser testados utilizando ferramentas como:

- Postman
- Insomnia
- curl

Fluxo recomendado:

```text
1. Cadastrar usuário
        ↓
2. Fazer login
        ↓
3. Receber JWT
        ↓
4. Enviar JWT como Bearer Token
        ↓
5. Acessar endpoint protegido
```

---

## Status do projeto

Em desenvolvimento.

Funcionalidades atualmente implementadas:

- [x] Cadastro de usuários
- [x] BCrypt
- [x] Verificação de e-mail duplicado
- [x] Login
- [x] Tratamento de credenciais inválidas
- [x] Geração de JWT
- [x] Validação de JWT
- [x] Endpoint protegido
- [x] Validação dos DTOs
- [x] Testes da camada web com MockMvc
- [ ] Testes de integração
- [ ] Refresh Token
- [ ] Roles e permissões
---

## Objetivo

Este projeto tem como objetivo praticar conceitos de desenvolvimento backend, incluindo:

- APIs REST
- arquitetura em camadas
- autenticação
- autorização
- segurança de senhas
- JWT
- persistência de dados
- tratamento de exceções
- integração com PostgreSQL

---

## Autor

Desenvolvido por **Ruan**.
