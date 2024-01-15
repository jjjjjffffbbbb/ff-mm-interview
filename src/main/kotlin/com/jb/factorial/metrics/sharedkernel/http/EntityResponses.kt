package com.jb.factorial.metrics.sharedkernel.http

import org.springframework.http.ResponseEntity

object EntityResponses {
    fun from(error: HttpError): ResponseEntity<*> =
        when (error) {
            is BadRequestError -> badRequest(*error.errors.toTypedArray())
        }

    private fun badRequest(vararg malformedError: RequestMalformedError): ResponseEntity<*> =
        ResponseEntity.badRequest().body(malformedError)
}