package com.jb.factorial.metrics.command.infrastructure.jpa

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricRepository
import org.springframework.stereotype.Component

@Component
class MetricRepositoryJpa: MetricRepository {

    override fun save(metric: Metric) {
        TODO()
    }
}