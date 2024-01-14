package com.jb.factorial.metrics.command.domain

import com.jb.factorial.metrics.config.KotestConfig
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.property.Arb
import io.kotest.property.arbitrary.Codepoint
import io.kotest.property.arbitrary.az
import io.kotest.property.arbitrary.choice
import io.kotest.property.arbitrary.constant
import io.kotest.property.arbitrary.string
import io.kotest.property.assume
import io.kotest.property.checkAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class NameTest {

    @Test
    fun `a string within 50 characters, all letters, is parsed correctly`() {
        runBlocking {
            checkAll(KotestConfig.NUMBER_TESTS, validNameGenerator) { value ->
                Name.parse(value) shouldBeRight Name(value)
            }
        }
    }

    @Test
    fun `an empty string cannot be parsed as Name`() {
        Name.parse("") shouldBeLeft NameParseError.EmptyString
    }

    @Test
    fun `a blank string cannot be parsed as Name`() {
        Name.parse("    ") shouldBeLeft NameParseError.EmptyString
    }

    @Test
    fun `a string with a character that is not a letter cannot be parsed as Name`() {
        runBlocking {
            checkAll(KotestConfig.NUMBER_TESTS, randomCharacterGenerator) { value ->
                assume(value.any { !it.isLetter() })

                Name.parse(value) shouldBeLeft NameParseError.InvalidCharacter
            }
        }
    }

    @Test
    fun `a string with a length bigger than 50 cannot be parsed as Name`() {
        runBlocking {
            checkAll(KotestConfig.NUMBER_TESTS, invalidLengthGenerator) { value ->
                Name.parse(value) shouldBeLeft NameParseError.InvalidLength
            }
        }
    }

    @Test
    fun `a Name cannot be instantiate when the String is no valid`() {
        runBlocking {
            checkAll(KotestConfig.NUMBER_TESTS, invalidNameGenerator) { value ->
                shouldThrow<IllegalArgumentException> { Name(value) }
            }
        }
    }

    companion object {
        private const val MIN_SIZE_NAME = 1
        private const val MAX_SIZE_NAME = 50

        private val validNameGenerator =
            Arb.string(minSize = MIN_SIZE_NAME, maxSize = MAX_SIZE_NAME, codepoints = Codepoint.az())
        private val randomCharacterGenerator =
            Arb.string(minSize = MIN_SIZE_NAME, maxSize = MAX_SIZE_NAME)
        private val invalidLengthGenerator =
            Arb.string(minSize = MAX_SIZE_NAME + 1, codepoints = Codepoint.az())
        private val invalidNameGenerator = Arb.choice(randomCharacterGenerator, invalidLengthGenerator, Arb.constant(""))
    }
}