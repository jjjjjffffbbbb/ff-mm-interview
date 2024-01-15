package com.jb.factorial.metrics.command.infrastructure.jpa

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricRepository
import com.jb.factorial.metrics.command.infrastructure.jpa.dao.MetricDao
import com.jb.factorial.metrics.command.infrastructure.jpa.dao.MetricEntity
import org.springframework.stereotype.Component

@Component
class MetricRepositoryJpa(
    private val dao: MetricDao,
): MetricRepository {

    override fun save(metric: Metric) {
        metric
            .let(::toEntity)
            .also(dao::save)
    }

    private fun toEntity(metric: Metric) =
        MetricEntity(
            id = metric.id.value,
            name = metric.name.value,
            timestamp = metric.timestamp.value,
            valueMetric = metric.value.value,
        )
}