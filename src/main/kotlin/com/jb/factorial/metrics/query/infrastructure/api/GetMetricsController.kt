package com.jb.factorial.metrics.query.infrastructure.api

import com.jb.factorial.metrics.query.application.GetMetrics
import com.jb.factorial.metrics.query.application.GetMetricsHandler
import com.jb.factorial.metrics.query.application.GroupBy
import com.jb.factorial.metrics.query.infrastructure.jpa.MetricProjection
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class GetMetricsResponse(val metrics: List<MetricProjection>)

@RestController
class GetMetricsController(
    private val handler: GetMetricsHandler
) {

    @GetMapping(URL)
    fun getMetrics(@RequestParam groupBy: String): ResponseEntity<*> =
        GroupBy.parse(groupBy)
            .map(::GetMetrics)
            .map(handler::handle)
            .map(::GetMetricsResponse)
            .fold( { throw IllegalStateException("error") }, ::ok)


    companion object {
        private const val URL = "/metrics"
    }
}