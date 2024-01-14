package com.jb.factorial.metrics.command.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import org.junit.jupiter.api.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

class TimestampTest {

    @Test
    fun `a string in UTC format is a valid Timestamp`() {
        val value = "2024-01-15T00:10:00Z"

        Timestamp.parse(value) shouldBeRight
                ZonedDateTime.of(2024, 1, 15, 0, 10, 0, 0, ZoneOffset.UTC)
                    .toInstant()
                    .let(::Timestamp)
    }

    @Test
    fun `a string in CET format is a valid Timestamp which will be saved in UTC`() {
        val value = "2024-01-15T01:10:00+01:00"

        Timestamp.parse(value) shouldBeRight
                ZonedDateTime.of(2024, 1, 15, 0, 10, 0, 0, ZoneOffset.UTC)
                    .toInstant()
                    .let(::Timestamp)
    }

    @Test
    fun `a string not in ISO date time zoned will not parse to Timestamp`() {
        val value = "this is not a valid string"

        Timestamp.parse(value) shouldBeLeft TimestampParseError.InvalidFormat
    }

    @Test
    fun `an empty string will not parse to Timestamp`() {
        val value = ""

        Timestamp.parse(value) shouldBeLeft TimestampParseError.InvalidFormat
    }

}