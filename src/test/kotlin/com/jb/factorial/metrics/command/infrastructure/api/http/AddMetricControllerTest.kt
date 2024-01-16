package com.jb.factorial.metrics.command.infrastructure.api.http

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jb.factorial.metrics.command.application.AddMetric
import com.jb.factorial.metrics.command.application.AddMetricHandler
import com.jb.factorial.metrics.command.domain.MetricId
import com.jb.factorial.metrics.command.domain.Name
import com.jb.factorial.metrics.command.domain.Timestamp
import com.jb.factorial.metrics.command.domain.Value
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.Instant

class AddMetricControllerTest {
    private val now = Instant.now()

    private val mapper = jacksonObjectMapper()
    private val handler: AddMetricHandler = mockk()
    private val controller = AddMetricController(handler)

    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `a valid request will return 200`() {
        val request = aRequest()
        val metricId = MetricId.generate()
        idToGenerate(metricId, request)

        executeRequest(request)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`(metricId.value)))
    }

    @Test
    fun `when one of the parameters is invalid the response will be 400 with the error`() {
        val request = aRequest(name = "")

        executeRequest(request)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$[0].field", `is`("name")))
            .andExpect(jsonPath("$[0].error", `is`("EmptyString")))
    }

    // Here would be more cases of tests...

    @Test
    fun `if several inputs are invalid, a list of errors will be returned`() {
        val request = aRequest(name = "", timestamp = "not timestamp")

        executeRequest(request)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$[0].field", `is`("name")))
            .andExpect(jsonPath("$[0].error", `is`("EmptyString")))
            .andExpect(jsonPath("$[1].field", `is`("timestamp")))
            .andExpect(jsonPath("$[1].error", `is`("InvalidFormat")))
    }

    private fun idToGenerate(id: MetricId, request: AddMetricRequest) {
        val command = AddMetric(Name(request.name), Timestamp(now), Value(request.value))

        every { handler.handle(command) } returns id
    }

    private fun executeRequest(request: AddMetricRequest) =
        mockMvc.perform(
            post(AddMetricController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))

    private fun aRequest(
        name: String = "metricName",
        timestamp: String = now.toString(),
        value: Float = 1234f,
    ) = AddMetricRequest(name, timestamp, value)
}