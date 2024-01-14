package com.jb.factorial.metrics.command.domain

import arrow.core.Either
import arrow.core.flatMap
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

sealed interface TimestampParseError {
    data object InvalidFormat: TimestampParseError
}

@JvmInline
value class Timestamp(val value: Instant) {

    companion object {
        private fun validate(value: String): Either<TimestampParseError, Instant> =
            Either.catch { ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME) }
                .mapLeft { TimestampParseError.InvalidFormat }
                .map { it.withZoneSameInstant(ZoneOffset.UTC) }
                .map { it.toInstant() }

        fun parse(value: String): Either<TimestampParseError, Timestamp> =
            validate(value).map(::Timestamp)
    }
}