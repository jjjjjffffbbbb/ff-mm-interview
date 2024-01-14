package com.jb.factorial.metrics.command.infrastructure

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.MetricRepository

class MetricRepositoryInMemory: MetricRepository {

    private val store: MutableMap<MetricId, Metric> = mutableMapOf()

    override fun save(metric: Metric) {
        store[metric.id] = metric
    }

    fun findBy(metricId: MetricId): Metric? = store[metricId]

}