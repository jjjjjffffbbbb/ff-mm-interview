package com.jb.factorial.metrics.command.application

import com.jb.factorial.metrics.command.domain.Metric
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import com.jb.factorial.metrics.command.infrastructure.MetricRepositoryInMemory
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Instant

class AddMetricHandlerTest {

    private val now = Instant.now()
    private val idToGenerate = MetricId.generate()

    private val repository = MetricRepositoryInMemory()
    private val handler = AddMetricHandler(repository) { idToGenerate }


    @Test
    fun `when the add metric command is handled, the metric will be persisted in the database`() {
        val command = anAddMetric()

        handler.handle(command)

        repository.findBy(idToGenerate) shouldBe Metric(idToGenerate, command.name, command.timestamp, command.value)
    }

    private fun anAddMetric() = AddMetric(Name("MetricName"), Timestamp(now), Value(123f))
}