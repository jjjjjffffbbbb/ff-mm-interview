package com.jb.factorial.metrics.command.application

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.MetricRepository
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

data class AddMetric(val name: Name, val timestamp: Timestamp, val value: Value)

@Component
class AddMetricHandler(
    private val repository: MetricRepository,
    private val idGenerator: () -> MetricId = { MetricId.generate() },
) {

    @Transactional
    fun handle(command: AddMetric): MetricId =
        Metric(idGenerator(), command.name, command.timestamp, command.value)
            .also(repository::save)
            .id
}