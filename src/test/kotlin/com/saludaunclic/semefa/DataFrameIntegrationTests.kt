package com.saludaunclic.semefa

import com.saludaunclic.semefa.gateway.GatewayApplication
import com.saludaunclic.semefa.gateway.dto.AppSetupDto
import com.saludaunclic.semefa.gateway.repository.UserRepository
import com.saludaunclic.semefa.gateway.service.dataframe.DataFrameService
import com.saludaunclic.semefa.gateway.service.setup.AppSetupService
import com.saludaunclic.semefa.util.TestDataUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate

@ContextConfiguration
@SpringBootTest(classes = [ GatewayApplication::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataFrameIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val appSetupService: AppSetupService,
    @Autowired val dataFrameService: DataFrameService,
    @Autowired val userRepository: UserRepository
) {
    val appSetup: AppSetupDto = TestDataUtils.generateAppSetup()
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
        val payload = TestDataUtils.generateIn271RegafiUpdate()
        val entity = restTemplate.postForEntity("/api/regafi/update271", payload, In997RegafiUpdate::class.java)
        assertNotNull(entity)
    }

    @AfterAll
    fun tearDown() {
        userRepository.deleteAll()
    }
}
