package com.cds.itemkeeper.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/actuator/health/liveness")
    fun liveness(): ResponseEntity<String> {
        return ResponseEntity.ok("Liveness: OK")
    }

    @GetMapping("/actuator/health/readiness")
    fun readiness(): ResponseEntity<String> {
        return ResponseEntity.ok("Readiness: OK")
    }
}