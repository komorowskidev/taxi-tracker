package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import pl.komorowskidev.taxitracker.infrastructure.mongodb.TaxiLocationRepository
import pl.komorowskidev.taxitracker.testutils.TaxiTrackerSpringBootTest
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant

@TaxiTrackerSpringBootTest
class TaxiLocationIT {
    @LocalServerPort
    private var port: Int = 0

    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var taxiLocationRepository: TaxiLocationRepository

    private val testDataFactory = TestDataFactory()

    private val requestedTaxiId = "taxi1"
    private val otherTaxiId = "taxi2"
    private val requestedTimeStartRange = "2023-05-01T00:00:00Z"
    private val requestedTimeEndRange = "2023-05-30T00:00:00Z"
    private val firstTimestampInRange = "2023-05-10T10:00:00Z"
    private val secondTimestampInRange = "2023-05-20T10:00:00Z"
    private val timestampOutsideRange = "2023-10-01T10:00:00Z"
    private val timestampBeforeTaxiLocations = "1900-01-01T10:00:00Z"

    @BeforeEach
    fun setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
        taxiLocationRepository.deleteAll()
        val entities =
            listOf(
                testDataFactory.createTaxiLocationEntity(taxiId = requestedTaxiId, timestamp = Instant.parse(firstTimestampInRange)),
                testDataFactory.createTaxiLocationEntity(taxiId = requestedTaxiId, timestamp = Instant.parse(timestampOutsideRange)),
                testDataFactory.createTaxiLocationEntity(taxiId = requestedTaxiId, timestamp = Instant.parse(secondTimestampInRange)),
                testDataFactory.createTaxiLocationEntity(taxiId = otherTaxiId, timestamp = Instant.parse(firstTimestampInRange)),
            )
        taxiLocationRepository.saveAll(entities)
    }

    @Test
    fun `should return taxi locations by given parameters`() {
        val response: List<TaxiLocationDto> =
            checkNotNull(
                webTestClient.get()
                    .uri(
                        "/api/taxi-locations?taxi-id=$requestedTaxiId&start-time=$requestedTimeStartRange&end-time=$requestedTimeEndRange",
                    ),
            )
                .exchange()
                .expectStatus().isOk
                .expectBody<List<TaxiLocationDto>>()
                .returnResult().responseBody!!

        assertEquals(2, response.size)
        val actualTaxiLocationDto1 = response[0]
        val actualTaxiLocationDto2 = response[1]
        assertEquals(requestedTaxiId, actualTaxiLocationDto1.taxiId)
        assertEquals(requestedTaxiId, actualTaxiLocationDto2.taxiId)
        assertEquals(firstTimestampInRange, actualTaxiLocationDto1.timestamp)
        assertEquals(secondTimestampInRange, actualTaxiLocationDto2.timestamp)
    }

    @Test
    fun `should return empty list when no taxi locations found by given parameters`() {
        val response: List<TaxiLocationDto> =
            checkNotNull(
                webTestClient.get()
                    .uri(
                        "/api/taxi-locations?taxi-id=$requestedTaxiId&start-time=$timestampBeforeTaxiLocations&end-time=$timestampBeforeTaxiLocations",
                    ),
            )
                .exchange()
                .expectStatus().isOk
                .expectBody<List<TaxiLocationDto>>()
                .returnResult().responseBody!!

        assertTrue(response.isEmpty())
    }

    @Test
    fun `should response with Bad Request when start-time is after end-time`() {
        checkNotNull(
            webTestClient.get()
                .uri(
                    "/api/taxi-locations?taxi-id=$requestedTaxiId&start-time=$requestedTimeEndRange&end-time=$requestedTimeStartRange",
                ),
        )
            .exchange()
            .expectStatus().isBadRequest
    }
}
