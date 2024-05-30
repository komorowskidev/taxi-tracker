package pl.komorowskidev.taxitracker.domain.service

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.komorowskidev.taxitracker.domain.exceptions.InvalidDateRangeException
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant

class TaxiLocationServiceTest {
    private val taxiLocationPortMock: TaxiLocationPort = mock()
    private val taxiLocationService = TaxiLocationService(taxiLocationPortMock)

    private val testDataFactory = TestDataFactory()

    @Test
    fun `saveTaxiLocation should call save method`() {
        // given
        val taxiLocation = testDataFactory.createTaxiLocation()

        // when
        taxiLocationService.saveTaxiLocation(taxiLocation)

        // then
        verify(taxiLocationPortMock).save(taxiLocation)
    }

    @Test
    fun `getTaxiLocations should return result from TaxiLocationPort`() {
        // given
        val taxiId = "abc"
        val startTime = Instant.now()
        val endTime = startTime.plusSeconds(10)
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)
        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = "1")
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = "2")
        val taxiLocations = listOf(taxiLocation1, taxiLocation2)
        whenever(taxiLocationPortMock.getTaxiLocations(taxiLocationRequest)).thenReturn(taxiLocations)

        // when
        val actual = taxiLocationService.getTaxiLocations(taxiLocationRequest)

        // then
        assertSame(taxiLocations, actual)
    }

    @Test
    fun `getTaxiLocations should return result from TaxiLocationPort when startTime equals endTime`() {
        // given
        val taxiId = "abc"
        val time = Instant.now()
        val taxiLocationRequest = TaxiLocationRequest(taxiId, time, time)
        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = "1")
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = "2")
        val taxiLocations = listOf(taxiLocation1, taxiLocation2)
        whenever(taxiLocationPortMock.getTaxiLocations(taxiLocationRequest)).thenReturn(taxiLocations)

        // when
        val actual = taxiLocationService.getTaxiLocations(taxiLocationRequest)

        // then
        assertSame(taxiLocations, actual)
    }

    @Test
    fun `getTaxiLocations should throw InvalidDateRangeException when start time is after end time`() {
        // given
        val taxiId = "abc"
        val startTime = Instant.now()
        val endTime = startTime.minusMillis(1)
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)

        // when
        assertThrows<InvalidDateRangeException> {
            taxiLocationService.getTaxiLocations(taxiLocationRequest)
        }
    }
}
