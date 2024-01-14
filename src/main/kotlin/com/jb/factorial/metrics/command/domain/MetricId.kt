package com.jb.factorial.metrics.command.domain

import java.util.*

@JvmInline
value class MetricId(val value: String) {

    companion object {
        fun generate(): MetricId = UUID.randomUUID().toString().let(::MetricId)
    }

}