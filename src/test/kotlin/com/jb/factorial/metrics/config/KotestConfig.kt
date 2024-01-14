package com.jb.factorial.metrics.config

import io.kotest.common.ExperimentalKotest
import io.kotest.property.PropTestConfig

@OptIn(ExperimentalKotest::class)
object KotestConfig {
    val NUMBER_TESTS: PropTestConfig =
        PropTestConfig(
            iterations = 50,
        )
}
