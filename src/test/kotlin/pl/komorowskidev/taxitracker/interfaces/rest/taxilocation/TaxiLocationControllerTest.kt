package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant

class TaxiLocationControllerTest {
    private val taxiLocationServiceMock: TaxiLocationService = mock()
    private val taxiLocationDtoMapperMock: TaxiLocationDtoMapper = mock()
    private val taxiLocationController = TaxiLocationController(taxiLocationServiceMock, taxiLocationDtoMapperMock)

    private val testDataFactory = TestDataFactory()

    @Test
    fun `should return taxi locations`() {
        // Given
        val taxiId = "taxiId"
        val endTime = Instant.now()
        val startTime = endTime.minusSeconds(10)
        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = "1")
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = "2")
        val taxiLocationDto1 = testDataFactory.createTaxiLocationDto(taxiId = "id1")
        val taxiLocationDto2 = testDataFactory.createTaxiLocationDto(taxiId = "id2")
        val captor = argumentCaptor<TaxiLocationRequest>()
        whenever(taxiLocationServiceMock.getTaxiLocations(captor.capture())).thenReturn(listOf(taxiLocation1, taxiLocation2))
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
        val response = actual.body!!
        assertTrue(response.size == 2)
        assertTrue(response.contains(taxiLocationDto1))
        assertTrue(response.contains(taxiLocationDto1))
    }
}
