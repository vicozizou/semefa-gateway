import com.saludaunclic.semefa.gateway.GatewayApplication
import com.saludaunclic.semefa.gateway.repository.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import util.TestDataUtils

@ContextConfiguration
@SpringBootTest(classes = [ GatewayApplication::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppSetupIntegrationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val userRepository: UserRepository
) {
    @BeforeAll
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun `Assert application is setup correctly`() {
        val entity = restTemplate.postForEntity(
            "/api/public/setup",
            TestDataUtils.generateAppSetup(),
            String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `Assert application is already set up`() {
        val entity = restTemplate.postForEntity(
            "/api/public/setup",
            TestDataUtils.generateAppSetup(),
            String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.CONFLICT)
    }

    @AfterAll
    fun tearDown() {
        userRepository.deleteAll()
    }
}
