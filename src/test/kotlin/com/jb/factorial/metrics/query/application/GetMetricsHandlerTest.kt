package com.jb.factorial.metrics.query.application

import com.jb.factorial.metrics.query.infrastructure.jpa.MetricProjectionDao
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class GetMetricsHandlerTest {

    private val daoProjection: MetricProjectionDao = mockk(relaxed = true)
    private val handler = GetMetricsHandler(daoProjection)

    @Test
    fun `querying by minutes will call the correct method of the dao`() {
        val query = GetMetrics(GroupBy.MINUTES)

        handler.handle(query)

        verify { daoProjection.getMetricsByMinutes() }
    }

    @Test
    fun `querying by hours will call the correct method of the dao`() {
        val query = GetMetrics(GroupBy.HOURS)

        handler.handle(query)

        verify { daoProjection.getMetricsByHours() }
    }

    @Test
    fun `querying by days will call the correct method of the dao`() {
        val query = GetMetrics(GroupBy.DAYS)

        handler.handle(query)

        verify { daoProjection.getMetricsByDays() }
    }
}