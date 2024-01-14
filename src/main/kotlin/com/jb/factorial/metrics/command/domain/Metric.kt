package com.jb.factorial.metrics.command.domain

data class Metric(
    val id: MetricId,
    val name: Name,
    val timestamp: Timestamp,
    val value: Value,
)