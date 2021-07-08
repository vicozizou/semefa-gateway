package com.saludaunclic.semefa.gateway.service.setup

import com.saludaunclic.semefa.gateway.GatewayConstants
import com.saludaunclic.semefa.gateway.dto.AppSetupDto
import com.saludaunclic.semefa.gateway.dto.RoleDto
import com.saludaunclic.semefa.gateway.model.Role
import com.saludaunclic.semefa.gateway.model.UserStatus
import com.saludaunclic.semefa.gateway.model.toModel
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
    fun setupApp(appSetup: AppSetupDto): String {
        if (userService.userCount() > 0) {
            throw ServiceException("Aplicación ya tiene usuario inicial definido", HttpStatus.BAD_REQUEST)
        }

        appSetup.user.apply {
            roles.clear()
            roles.add(RoleDto(name = GatewayConstants.SUPER_ROLE))
            status = UserStatus.ENABLED
        }

        logger.info("Attempting to setup application with user: $appSetup")
        val user = userService.save(toModel(appSetup.user))

        return """Aplicación fue inicializada con:
            $user
        """.trimMargin()
    }
}
