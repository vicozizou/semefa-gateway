package com.saludaunclic.semefa.gateway.service.setup

import com.saludaunclic.semefa.gateway.GatewayConstants
import com.saludaunclic.semefa.gateway.model.AppSetup
import com.saludaunclic.semefa.gateway.model.RoleRef
import com.saludaunclic.semefa.gateway.model.User
import com.saludaunclic.semefa.gateway.model.UserStatus
import com.saludaunclic.semefa.gateway.service.user.UserService
import com.saludaunclic.semefa.gateway.throwing.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AppSetupService(val userService: UserService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Throws(ServiceException::class)
    fun setupApp(appSetup: AppSetup): String {
        if (userService.userCount() > 1) {
            throw ServiceException("Aplicación ya tiene definidos usuarios", HttpStatus.BAD_REQUEST)
        }

        logger.info("Attempting to setup application with user: ${appSetup.username}")
        val user = userService.save(User(
            username = appSetup.username,
            password = appSetup.password
        ).apply {
            roles = setOf(RoleRef(GatewayConstants.SUPER_ROLE))
            status = UserStatus.ENABLED
        })

        return """Aplicación fue inicializada con:
            $user
        """.trimMargin()
    }
}
