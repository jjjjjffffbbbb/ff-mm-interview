package com.jb.factorial.metrics.query.infrastructure.jpa

import com.jb.factorial.metrics.RepositoryIntegrationTest
import com.jb.factorial.metrics.command.domain.MetricId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@RepositoryIntegrationTest
class MetricProjectionDaoTest {

    @Autowired
    private lateinit var projectionDao: MetricProjectionDao

    private val metricSamples = listOf(
        entityOf(todayAt(22, 12, 5), 1f),
        entityOf(todayAt(22, 12, 10), 2f),
        entityOf(todayAt(22, 12, 15), 3f),
        entityOf(todayAt(22, 30, 15), 1f),
        entityOf(todayAt(22, 30, 30), 3f),
        entityOf(tomorrowAt(23, 34, 1), 5f),
    )

    @BeforeEach
    fun setup() {
        projectionDao.saveAll(metricSamples)
    }

    @AfterEach
    fun cleanup() {
        projectionDao.deleteAll()
    }


    @Test
    fun `the projection can return metrics grouped by minutes`() {
        val result = projectionDao.getMetricsByMinutes().map { MetricProjection(it.time, it.average) }

        val expected = listOf(
            MetricProjection("$todayDate 22:12", 2f),
            MetricProjection("$todayDate 22:30", 2f),
            MetricProjection("$tomorrowDate 23:34", 5f),
        )
        result shouldBe expected
    }

    @Test
    fun `the projection can return metrics grouped by hours`() {
        val result = projectionDao.getMetricsByHours().map { MetricProjection(it.time, it.average) }

        val expected = listOf(
            MetricProjection("$todayDate 22", 2f),
            MetricProjection("$tomorrowDate 23", 5f),
        )
        result shouldBe expected
    }

    @Test
    fun `the projection can return metrics grouped by days`() {
        val result = projectionDao.getMetricsByDays().map { MetricProjection(it.time, it.average) }

        val expected = listOf(
            MetricProjection("$todayDate", 2f),
            MetricProjection("$tomorrowDate", 5f),
        )
        result shouldBe expected
    }


    private fun todayAt(hour: Int, minute: Int, second: Int) =
        LocalDateTime.of(todayDate, LocalTime.of(hour, minute, second)).toInstant(ZoneOffset.UTC)


    private fun tomorrowAt(hour: Int, minute: Int, second: Int) =
        LocalDateTime.of(tomorrowDate, LocalTime.of(hour, minute, second)).toInstant(ZoneOffset.UTC)

    private fun entityOf(timestamp: Instant, value: Float) =
        MetricEntity(
            MetricId.generate().value,
            "aName",
            timestamp,
            value,
        )


    companion object {
        private val todayDate = LocalDate.now()
        private val tomorrowDate = todayDate.plusDays(1)
    }


}