package com.saludaunclic.semefa.gateway.controller

import com.saludaunclic.semefa.gateway.model.AppSetup
import com.saludaunclic.semefa.gateway.service.setup.AppSetupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/public/setup")
class AppSetupController(val appSetupService: AppSetupService) {
    @PostMapping
    fun setupApp(@RequestBody appSetup:AppSetup): ResponseEntity<String> =
        ResponseEntity
            .ok(appSetupService.setupApp(appSetup))
}