package com.saludaunclic.semefa

import com.saludaunclic.semefa.gateway.GatewayApplication
import com.saludaunclic.semefa.gateway.dto.AppSetupDto
import com.saludaunclic.semefa.gateway.repository.UserRepository
import com.saludaunclic.semefa.gateway.service.dataframe.DataFrameService
import com.saludaunclic.semefa.gateway.service.setup.AppSetupService
import com.saludaunclic.semefa.util.TestDataUtils
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(classes = [ GatewayApplication::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataFrameIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val appSetupService: AppSetupService,
    @Autowired val dataFrameService: DataFrameService,
    @Autowired val userRepository: UserRepository
) {
    val appSetup: AppSetupDto = TestDataUtils.createAppSetup()
    lateinit var token: String

    @BeforeAll
    fun setup() {
        with(appSetup.user) {
            appSetupService.setupApp(appSetup)
            token = TestDataUtils.loginWithCredentials(restTemplate, username, password).body ?: ""
        }
    }

    @Test
    fun `Assert post a Regafi 271 payload`() {

    }

    @AfterAll
    fun tearDown() {
        userRepository.deleteAll()
    }
}
