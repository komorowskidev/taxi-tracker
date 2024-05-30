package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.komorowskidev.taxitracker.testutils.TestDataFactory

class TaxiLocationMapperTest {
    private val mapper = TaxiLocationMapper()
    private val testDataFactory = TestDataFactory()

    @Test
    fun `toEntity should map TaxiLocation to TaxiLocationEntity correctly`() {
        // Given
        val taxiLocation = testDataFactory.createTaxiLocation()

        // When
        val entity = mapper.toEntity(taxiLocation)

        // Then
        assertEquals(taxiLocation.taxiId, entity.taxiId)
        assertEquals(taxiLocation.latitude, entity.latitude)
        assertEquals(taxiLocation.longitude, entity.longitude)
        assertEquals(taxiLocation.timestamp, entity.timestamp)
    }

    @Test
    fun `toDomain should map TaxiLocationEntity to TaxiLocation correctly`() {
        // Given
        val entity = testDataFactory.createTaxiLocationEntity()

        // When
        val taxiLocation = mapper.toDomain(entity)

        // Then
        assertEquals(entity.taxiId, taxiLocation.taxiId)
        assertEquals(entity.latitude, taxiLocation.latitude)
        assertEquals(entity.longitude, taxiLocation.longitude)
        assertEquals(entity.timestamp, taxiLocation.timestamp)
    }
}
