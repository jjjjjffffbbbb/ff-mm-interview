package com.jb.factorial.metrics.command.infrastructure.jpa.dao

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import java.time.Instant

@Entity
@Table(name = "METRICS")
data class MetricEntity(
    @Id
    val id: String,
    val name: String,
    val timestamp: Instant,
    val valueMetric: Float,
)

interface MetricDao: CrudRepository<MetricEntity, String>