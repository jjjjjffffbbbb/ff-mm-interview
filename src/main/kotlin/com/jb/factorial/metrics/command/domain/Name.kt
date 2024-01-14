package com.jb.factorial.metrics.command.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right

sealed interface NameParseError {
    data object EmptyString: NameParseError
    data object InvalidCharacter: NameParseError
    data object InvalidLength: NameParseError
}

@JvmInline
value class Name(val value: String) {

    init {
        require(validate(value).isRight()) { "Name cannot be instantiate with value $value" }
    }

    companion object {
        private fun validate(value: String): Either<NameParseError, String> = when {
            value.isBlank() -> NameParseError.EmptyString.left()
            value.length > MAX_LENGTH -> NameParseError.InvalidLength.left()
            value.any { !it.isLetter() } -> NameParseError.InvalidCharacter.left()
            else -> value.right()
        }

        fun parse(value: String): Either<NameParseError, Name> = validate(value).map(::Name)

        private const val MAX_LENGTH = 50
    }
}