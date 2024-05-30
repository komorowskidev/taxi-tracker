package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.testutils.TestDataFactory
import java.time.Instant

class TaxiLocationAdapterTest {
    private val taxiLocationRepositoryMock: TaxiLocationRepository = mock()
    private val taxiLocationMapperMock: TaxiLocationMapper = mock()

    private val taxiAdapter = TaxiLocationAdapter(taxiLocationRepositoryMock, taxiLocationMapperMock)

    private val testDataFactory = TestDataFactory()

    @Test
    fun `save should convert TaxiLocation to entity and save to repository`() {
        // Given
        val taxiLocation = testDataFactory.createTaxiLocation()
        val taxiLocationEntity = testDataFactory.createTaxiLocationEntity()
        whenever(taxiLocationMapperMock.toEntity(taxiLocation)).thenReturn(taxiLocationEntity)

        // When
        taxiAdapter.save(taxiLocation)

        // Then
        verify(taxiLocationRepositoryMock).save(taxiLocationEntity)
    }

    @Test
    fun `getTaxiLocations should retrieve TaxiLocationEntities and return converted to domain`() {
        val taxiId = "12345"
        val start = Instant.parse("2023-05-01T00:00:00Z")
        val end = Instant.parse("2023-05-01T23:59:59Z")
        val taxiLocationRequest = TaxiLocationRequest(taxiId, start, end)
        val taxiLocationEntity1 = testDataFactory.createTaxiLocationEntity(taxiId = "id1")
        val taxiLocationEntity2 = testDataFactory.createTaxiLocationEntity(taxiId = "id2")
        val taxiLocationEntities = listOf(taxiLocationEntity1, taxiLocationEntity2)

        val taxiLocation1 = testDataFactory.createTaxiLocation(taxiId = "id1")
        val taxiLocation2 = testDataFactory.createTaxiLocation(taxiId = "id2")

        whenever(
            taxiLocationRepositoryMock.findByTaxiIdAndTimestampBetweenOrderByTimestamp(taxiId, start, end),
        ).thenReturn(taxiLocationEntities)
        whenever(taxiLocationMapperMock.toDomain(taxiLocationEntity1)).thenReturn(taxiLocation1)
        whenever(taxiLocationMapperMock.toDomain(taxiLocationEntity2)).thenReturn(taxiLocation2)

        // When
        val actual = taxiAdapter.getTaxiLocations(taxiLocationRequest)

        assertEquals(2, actual.size)
        assertTrue(actual.contains(taxiLocation1))
        assertTrue(actual.contains(taxiLocation2))
    }
}
