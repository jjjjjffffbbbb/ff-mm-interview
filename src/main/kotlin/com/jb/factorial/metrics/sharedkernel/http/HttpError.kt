package com.jb.factorial.metrics.sharedkernel.http

import arrow.core.NonEmptyList

sealed interface HttpError
data class BadRequestError(val errors: NonEmptyList<RequestMalformedError>) : HttpError