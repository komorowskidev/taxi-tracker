package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationsResponse
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant
import kotlin.random.Random

class TaxiLocationControllerTest {
    private val taxiLocationServiceMock: TaxiLocationService = mock()
    private val taxiLocationDtoMapperMock: TaxiLocationDtoMapper = mock()
    private val taxiLocationController = TaxiLocationController(taxiLocationServiceMock, taxiLocationDtoMapperMock)

    private val testDataFactory = TestDataFactory()

    @Test
    fun `should return taxi locations`() {
        // Given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(Random.nextLong(100))
        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = RandomStringUtils.randomAlphanumeric(10))
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = RandomStringUtils.randomAlphanumeric(10))
        val taxiLocationDto1 = testDataFactory.createTaxiLocationDto(taxiId = RandomStringUtils.randomAlphanumeric(10))
        val taxiLocationDto2 = testDataFactory.createTaxiLocationDto(taxiId = RandomStringUtils.randomAlphanumeric(10))
        val taxiLocationsResponse = TaxiLocationsResponse.Success(listOf(taxiLocation1, taxiLocation2))
        val captor = argumentCaptor<TaxiLocationRequest>()
        whenever(taxiLocationServiceMock.getTaxiLocations(captor.capture())).thenReturn(taxiLocationsResponse)
        whenever(taxiLocationDtoMapperMock.toDto(taxiLocation1)).thenReturn(taxiLocationDto1)
        whenever(taxiLocationDtoMapperMock.toDto(taxiLocation2)).thenReturn(taxiLocationDto2)

        // When
        val actual = taxiLocationController.getTaxiLocations(taxiId, startTime, endTime)

        // Then
        val request = captor.firstValue
        assertEquals(taxiId, request.taxiId)
        assertEquals(startTime, request.startTime)
        assertEquals(endTime, request.endTime)
        assertTrue(actual.statusCode.is2xxSuccessful)
        val response = actual.body!!.data!!
        assertTrue(response.size == 2)
        assertTrue(response.contains(taxiLocationDto1))
        assertTrue(response.contains(taxiLocationDto1))
    }

    @Test
    fun `should throw ResponseStatusException when RequestNotValid`() {
        // Given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(Random.nextLong(100))
        val errorMessage = RandomStringUtils.randomAlphanumeric(15)
        val taxiLocationsResponse = TaxiLocationsResponse.RequestNotValid(errorMessage)
        whenever(taxiLocationServiceMock.getTaxiLocations(any())).thenReturn(taxiLocationsResponse)

        // When
        val actual = taxiLocationController.getTaxiLocations(taxiId, startTime, endTime)

        // Then
        verify(taxiLocationDtoMapperMock, never()).toDto(any())
        assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(actual.body!!.errorMessage).isEqualTo(errorMessage)
    }
}
