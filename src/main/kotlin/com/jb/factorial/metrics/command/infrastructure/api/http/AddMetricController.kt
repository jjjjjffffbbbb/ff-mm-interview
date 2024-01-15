package com.jb.factorial.metrics.command.infrastructure.api.http

import arrow.core.Either.Companion.zipOrAccumulate
import arrow.core.right
import com.jb.factorial.metrics.command.application.AddMetric
import com.jb.factorial.metrics.command.application.AddMetricHandler
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import com.jb.factorial.metrics.sharedkernel.http.BadRequestError
import com.jb.factorial.metrics.sharedkernel.http.EntityResponses
import com.jb.factorial.metrics.sharedkernel.http.EntityResponses.from
import com.jb.factorial.metrics.sharedkernel.http.RequestMalformedError
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private data class AddMetricRequest(val name: String, val timestamp: String, val value: Float)
private data class AddMetricResponse(val id: String)

@RestController
private class AddMetricController(
    private val addMetricHandler: AddMetricHandler,
) {

    @PostMapping("/metrics")
    fun addMetric(@RequestBody request: AddMetricRequest): ResponseEntity<*> =
        parseCommand(request)
            .mapLeft(::BadRequestError)
            .map(addMetricHandler::handle)
            .map { it.value }
            .fold(
                ::from,
                ::ok,
            )

    private fun parseCommand(request: AddMetricRequest) =
        zipOrAccumulate(
            parseName(request.name),
            parseTimestamp(request.timestamp),
            parseValue(request.value),
            ::AddMetric
        )

    private fun parseName(name: String) =
        Name.parse(name)
            .mapLeft { RequestMalformedError(NAME_FIELD, it.toString()) }

    private fun parseTimestamp(timestamp: String) =
        Timestamp.parse(timestamp)
            .mapLeft { RequestMalformedError(TIMESTAMP_FIELD, it.toString()) }

    private fun parseValue(value: Float) = Value(value).right()

    companion object {
        private const val NAME_FIELD = "name"
        private const val TIMESTAMP_FIELD = "timestamp"
    }

}