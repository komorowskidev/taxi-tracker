package pl.komorowskidev.taxitracker.domain.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant
import kotlin.random.Random

class TaxiLocationServiceTest {
    private val taxiLocationPortMock: TaxiLocationPort = mock()
    private val taxiLocationValidatorMock: TaxiLocationValidator = mock()
    private val taxiLocationService = TaxiLocationService(taxiLocationPortMock, taxiLocationValidatorMock)

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
    fun `getTaxiLocations should return result from TaxiLocationPort when validator returns Valid`() {
        // given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val startTime = Instant.now()
        val endTime = startTime.plusSeconds(Random.nextLong(100))
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)
        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = RandomStringUtils.randomAlphanumeric(20))
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = RandomStringUtils.randomAlphanumeric(20))
        val taxiLocations = listOf(taxiLocation1, taxiLocation2)
        whenever(taxiLocationValidatorMock.validate(taxiLocationRequest)).thenReturn(Validation.Valid)
        whenever(taxiLocationPortMock.getTaxiLocations(taxiLocationRequest)).thenReturn(taxiLocations)

        // when
        val actual = taxiLocationService.getTaxiLocations(taxiLocationRequest)

        // then
        assertThat(actual).isInstanceOf(TaxiLocationsResponse.Success::class.java)
        assertThat((actual as TaxiLocationsResponse.Success).taxiLocations).isSameAs(taxiLocations)
    }

    @Test
    fun `getTaxiLocations should return RequestNotValid when validator returns Invalid`() {
        // given
        val taxiId = RandomStringUtils.randomAlphanumeric(20)
        val startTime = Instant.now()
        val endTime = startTime.plusSeconds(Random.nextLong(100))
        val taxiLocationRequest = TaxiLocationRequest(taxiId, startTime, endTime)
        whenever(taxiLocationValidatorMock.validate(taxiLocationRequest)).thenReturn(Validation.Invalid("test"))

        // when
        val actual = taxiLocationService.getTaxiLocations(taxiLocationRequest)

        // then
        assertThat(actual).isInstanceOf(TaxiLocationsResponse.RequestNotValid::class.java)
        verify(taxiLocationPortMock, never()).getTaxiLocations(any())
    }
}
