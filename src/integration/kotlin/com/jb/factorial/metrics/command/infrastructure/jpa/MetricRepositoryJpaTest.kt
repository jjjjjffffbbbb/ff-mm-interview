package com.jb.factorial.metrics.command.infrastructure.jpa

import com.jb.factorial.metrics.RepositoryIntegrationTest
import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.MetricRepository
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import com.jb.factorial.metrics.command.infrastructure.jpa.dao.MetricDao
import com.jb.factorial.metrics.command.infrastructure.jpa.dao.MetricEntity
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant

@RepositoryIntegrationTest
class MetricRepositoryJpaTest {

    @Autowired
    private lateinit var dao: MetricDao

    private val repository: MetricRepository by lazy { MetricRepositoryJpa(dao) }

    @Test
    fun `a metric can be saved and retrieved`() {
        val metric = Metric(MetricId.generate(), Name("aaa"), Timestamp(Instant.now()), Value(123f))

        repository.save(metric)

        dao.findByIdOrNull(metric.id.value) shouldBe
                MetricEntity(metric.id.value, metric.name.value, metric.timestamp.value, metric.value.value)
    }

}