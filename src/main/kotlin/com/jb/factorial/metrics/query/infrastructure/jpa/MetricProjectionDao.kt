package com.jb.factorial.metrics.query.infrastructure.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.Instant


@Entity(name = "queryMetricEntity")
@Table(name = "METRICS")
data class MetricEntity(
    @Id
    val id: String,
    val name: String,
    val timestamp: Instant,
    val valueMetric: Float,
)

interface MetricProjectionEntity {
    val time: String
    val average: Float
}

interface MetricProjectionDao: CrudRepository<MetricEntity, String> {

    @Query(""" SELECT to_char(m.timestamp, 'YYYY-MM-DD HH24:MI') AS time, AVG(m.value_metric) AS average
        FROM METRICS.METRICS m 
        GROUP BY to_char(m.timestamp, 'YYYY-MM-DD HH24:MI')""", nativeQuery = true)
    fun getMetricsByMinutes(): List<MetricProjectionEntity>

    @Query(""" SELECT to_char(m.timestamp, 'YYYY-MM-DD HH24') AS time, AVG(m.value_metric) AS average
        FROM METRICS.METRICS m 
        GROUP BY to_char(m.timestamp, 'YYYY-MM-DD HH24')""", nativeQuery = true)
    fun getMetricsByHours(): List<MetricProjectionEntity>

    @Query(""" SELECT to_char(m.timestamp, 'YYYY-MM-DD') AS time, AVG(m.value_metric) AS average
        FROM METRICS.METRICS m 
        GROUP BY to_char(m.timestamp, 'YYYY-MM-DD')""", nativeQuery = true)
    fun getMetricsByDays(): List<MetricProjectionEntity>
}

