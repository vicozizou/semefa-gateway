package com.saludaunclic.semefa.util

import com.saludaunclic.semefa.gateway.dto.AppSetupDto
import com.saludaunclic.semefa.gateway.dto.UserDto
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

class TestDataUtils {
    companion object {
        const val TEST_ADMIN_USER = "testAdmin"
        const val TEST_ADMIN_PASSWORD = "test123"

        fun createAppSetup(username: String = TEST_ADMIN_USER, password: String = TEST_ADMIN_PASSWORD) =
            AppSetupDto(UserDto(username = username, password = password))

        fun loginWithCredentials(restTemplate: TestRestTemplate,
                                 username: String,
                                 password: String): ResponseEntity<String> =
            restTemplate.postForEntity(
                "/api/public/auth/login?username=$username&password=$password",
                null,
                String::class.java)
    }
}
