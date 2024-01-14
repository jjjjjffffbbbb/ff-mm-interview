package com.jb.factorial.metrics.command.domain

interface MetricRepository {
    fun save(metric: Metric)
}