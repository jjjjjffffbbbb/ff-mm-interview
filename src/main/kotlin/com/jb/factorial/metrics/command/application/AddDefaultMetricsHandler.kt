package com.jb.factorial.metrics.command.application

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.MetricRepository
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class AddDefaultMetricsHandler(
    private val repository: MetricRepository,
) {

    @Transactional
    fun handle() {
        listOf(
            metricOf("value", "2024-03-10T11:00:00Z", 3.0f),
            metricOf("value", "2024-03-10T11:00:10Z", 6.0f),
            metricOf("value", "2024-03-10T11:00:15Z", 2.0f),
            metricOf("value", "2024-03-10T11:10:00Z", 10.0f),
            metricOf("value", "2024-03-10T11:10:10Z", 15.0f),
            metricOf("value", "2024-03-10T11:10:15Z", 2.0f),
            metricOf("value", "2024-03-10T12:00:00Z", 5.0f),
            metricOf("value", "2024-03-10T12:05:00Z", 3.0f),
            metricOf("value", "2024-03-10T12:10:00Z", 7.0f),
            metricOf("value", "2024-03-10T12:15:00Z", 0.0f),
            metricOf("value", "2024-03-10T12:20:00Z", 2.0f),

        )
            // This is a testing case, so I wouldn't want to change the repository interface to support a testing use case
            // For that case, I do a less optimize way of saving data instead of doing a saveAll
            .forEach(repository::save)
    }

    private fun metricOf(name: String, timestamp: String, value: Float) =
        Metric(MetricId.generate(), Name(name), Timestamp(timestamp.toInstant()), Value(value))

    private fun String.toInstant() =
        ZonedDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            .withZoneSameInstant(ZoneOffset.UTC)
            .toInstant()
}