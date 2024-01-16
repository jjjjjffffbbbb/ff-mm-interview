package com.jb.factorial.metrics.query.application

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.junit.jupiter.api.Test

class GroupByTest {

    @Test
    fun `a value from GroupBy will be parsed as GroupBy`() {
        GroupBy.parse("MINUTES") shouldBeRight GroupBy.MINUTES
        GroupBy.parse("HOURS") shouldBeRight GroupBy.HOURS
        GroupBy.parse("DAYS") shouldBeRight GroupBy.DAYS
    }

    @Test
    fun `any string that is not a value of GroupBy will not be parsed`() {
        GroupBy.parse("value") shouldBeLeft InvalidGroupBy
    }

}