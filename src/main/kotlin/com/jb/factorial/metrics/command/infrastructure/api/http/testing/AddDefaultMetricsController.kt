package com.jb.factorial.metrics.command.infrastructure.api.http.testing

import com.jb.factorial.metrics.command.application.AddDefaultMetricsHandler
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AddDefaultMetricsController(
    private val addDefaultMetricsHandler: AddDefaultMetricsHandler,
) {

    @PostMapping("/testing/add-default-metrics")
    fun addDefaultMetrics(): ResponseEntity<*> =
        addDefaultMetricsHandler.handle()
            .let { ok().build<Nothing>()}
}