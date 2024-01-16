package com.jb.factorial.metrics.query.application

import arrow.core.Either
import com.jb.factorial.metrics.query.application.GroupBy.DAYS
import com.jb.factorial.metrics.query.application.GroupBy.HOURS
import com.jb.factorial.metrics.query.application.GroupBy.MINUTES
import com.jb.factorial.metrics.query.infrastructure.jpa.MetricProjection
import com.jb.factorial.metrics.query.infrastructure.jpa.MetricProjectionDao
import org.springframework.stereotype.Component

data object InvalidGroupBy

enum class GroupBy {
    MINUTES, HOURS, DAYS;

    companion object {
        fun parse(value: String): Either<InvalidGroupBy, GroupBy> =
            Either.catch { GroupBy.valueOf(value) }.mapLeft { InvalidGroupBy }
    }
}

@JvmInline
value class GetMetrics(val groupBy: GroupBy)

@Component
class GetMetricsHandler(
    private val dao: MetricProjectionDao,
) {

    fun handle(query: GetMetrics): List<MetricProjection> =
        when (query.groupBy) {
            MINUTES -> dao.getMetricsByMinutes()
            HOURS -> dao.getMetricsByHours()
            DAYS -> dao.getMetricsByDays()
        }
            .map { MetricProjection(it.time, it.average) }
}