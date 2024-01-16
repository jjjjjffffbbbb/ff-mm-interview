package com.jb.factorial.metrics.query.infrastructure.api

import com.jb.factorial.metrics.query.application.GetMetrics
import com.jb.factorial.metrics.query.application.GetMetricsHandler
import com.jb.factorial.metrics.query.application.GroupBy
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetMetricsController(
    private val handler: GetMetricsHandler
) {

    @GetMapping(URL)
    fun getMetrics(@RequestParam groupBy: String): ResponseEntity<*> {

        GroupBy.parse(groupBy)
            .map { GetMetrics(it) }
            .map { handler.handle(it) }
        return TODO()
    }


    companion object {
        const val URL = "/metrics"
    }
}