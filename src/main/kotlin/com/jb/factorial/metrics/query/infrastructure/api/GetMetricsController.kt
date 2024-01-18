package com.jb.factorial.metrics.query.infrastructure.api

import arrow.core.nonEmptyListOf
import com.jb.factorial.metrics.command.infrastructure.api.http.AddMetricController
import com.jb.factorial.metrics.query.application.GetMetrics
import com.jb.factorial.metrics.query.application.GetMetricsHandler
import com.jb.factorial.metrics.query.application.GroupBy
import com.jb.factorial.metrics.query.application.InvalidGroupBy
import com.jb.factorial.metrics.query.infrastructure.jpa.MetricProjection
import com.jb.factorial.metrics.sharedkernel.http.BadRequestError
import com.jb.factorial.metrics.sharedkernel.http.EntityResponses.from
import com.jb.factorial.metrics.sharedkernel.http.RequestMalformedError
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
            .mapLeft { it.toBadRequest() }
            .map(::GetMetrics)
            .map(handler::handle)
            .map(::GetMetricsResponse)
            .fold( ::from, ::ok)


    private fun InvalidGroupBy.toBadRequest() =
        nonEmptyListOf(RequestMalformedError(GROUP_BY_FIELD, ERROR_MESSAGE))
            .let(::BadRequestError)

    companion object {
        private const val URL = "/metrics"
        private const val GROUP_BY_FIELD = "groupBy"
        private val ERROR_MESSAGE = "Unknown groupBy. Valid values: ${GroupBy.entries.joinToString { it.name }}"
    }
}